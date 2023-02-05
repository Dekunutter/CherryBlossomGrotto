package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.client.network.handlers.DoubleJumpClientMessageHandler;
import com.deku.cherryblossomgrotto.client.network.messages.DoubleJumpClientMessage;
import com.deku.cherryblossomgrotto.common.blocks.*;
import com.deku.cherryblossomgrotto.common.capabilities.DoubleJumpCapability;
import com.deku.cherryblossomgrotto.common.enchantments.ModEnchantmentInitializer;
import com.deku.cherryblossomgrotto.common.entity.EntityTypeInitializer;
import com.deku.cherryblossomgrotto.common.entity.ModBlockEntities;
import com.deku.cherryblossomgrotto.common.entity.ModEntityType;
import com.deku.cherryblossomgrotto.common.entity.ai.sensing.ModSensorTypes;
import com.deku.cherryblossomgrotto.common.entity.animal.tanooki.Tanooki;
import com.deku.cherryblossomgrotto.common.entity.npc.ModVillagerTypes;
import com.deku.cherryblossomgrotto.common.features.*;
import com.deku.cherryblossomgrotto.common.items.*;
import com.deku.cherryblossomgrotto.common.particles.ModParticles;
import com.deku.cherryblossomgrotto.common.recipes.ModRecipeSerializerInitializer;
import com.deku.cherryblossomgrotto.common.sounds.ModSoundEvents;
import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeProvider;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModSurfaceRules;
import com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider.CherryBlossomForestFlowerProviderType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.placements.ModPlacements;
import com.deku.cherryblossomgrotto.common.world.gen.structures.*;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.*;
import com.deku.cherryblossomgrotto.server.network.handlers.DoubleJumpServerMessageHandler;
import com.deku.cherryblossomgrotto.server.network.messages.DoubleJumpServerMessage;
import com.deku.cherryblossomgrotto.utils.LogTweaker;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.deku.cherryblossomgrotto.common.enchantments.ModEnchantmentInitializer.DOUBLE_JUMP_ENCHANTMENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main
{
    // TODO: Set to true to hide noise on console when mod is finished
    final boolean HIDE_CONSOLE_NOISE = false;

    // declare Mod ID
    public static final String MOD_ID = "cherryblossomgrotto";

    // Initialize logger
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    // Network Protocol Version
    public static final String NETWORK_PROTOCOL_VERSION = "1.0";

    // Network channel
    public static SimpleChannel NETWORK_CHANNEL = null;

    /**
     * Constructor for initializing the mod.
     * Handles the setup of:
     *      - Log filtering.
     *      - Event Bus listeners
     *      - Registries
     *      - Ensuring client-only registrars only execute on a client
     *      - Ensures that mod structure piece types are registered early
     *      - Ensures that biomes are registered early
     *      - Adds additional forge event listeners for biome and world loading events
     */
    public Main() {
        System.out.println("STARTING EXECUTION");
        NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "cherryblossomgrottochannel"), () -> NETWORK_PROTOCOL_VERSION, DoubleJumpClientMessageHandler::isProtocolAcceptedOnClient, DoubleJumpServerMessageHandler::isProtocolAcceptedOnServer);

        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Sound events logic
        ModSoundEvents.SOUND_EVENTS.register(eventBus);

        // Biome logic
        ModBiomeInitializer.BIOMES.register(eventBus);
        ModBiomeInitializer.registerBiomes();

        // Structure logic
        System.out.println("REGISTER STRUCTURE TYPES");
        ModStructureTypeInitializer.STRUCTURE_TYPES.register(eventBus);

        // Enchantment logic
        ModEnchantmentInitializer.ENCHANTMENTS.register(eventBus);

        // Trunk Placer Types
        ModTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(eventBus);

        // AI Sensor Types
        ModSensorTypes.SENSOR_TYPES.register(eventBus);

        // Custom recipe serializers
        ModRecipeSerializerInitializer.RECIPE_SERIALIZERS.register(eventBus);

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);

        ClientOnlyRegistrar clientOnlyRegistrar = new ClientOnlyRegistrar(eventBus);

        // Register ourselves for server and other game events we are interested in
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.register(this);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientOnlyRegistrar::registerClientOnlyEvents);
    }

    /**
     * Sets up logic that is common to both the client and server
     *
     * In this case we are:
     * - Registering our custom network messages to the simple network channel.
     * - Registering our custom wood types so that we can use associated resources.
     * - Registering all our terrablender regions
     * - Registering all our different features (trees, vegetation, ores, miscellaneous)
     * - Registering all our placements (ensuring village placements register after the processor lists)
     * - Registering all our processor lists
     * - Registering our custom villager types
     * - Initializing all our modded structures, their pieces and the structure sets that they belong to.
     *
     * @param event The setup event
     */
    private void setup(final FMLCommonSetupEvent event) {
        NETWORK_CHANNEL.registerMessage(DoubleJumpServerMessage.MESSAGE_ID, DoubleJumpServerMessage.class, DoubleJumpServerMessage::encode, DoubleJumpServerMessage::decode, DoubleJumpServerMessageHandler::onMessageReceived, Optional.of(PLAY_TO_SERVER));
        NETWORK_CHANNEL.registerMessage(DoubleJumpClientMessage.MESSAGE_ID, DoubleJumpClientMessage.class, DoubleJumpClientMessage::encode, DoubleJumpClientMessage::decode, DoubleJumpClientMessageHandler::onMessageReceived, Optional.of(PLAY_TO_CLIENT));

        event.enqueueWork(() -> {
            WoodType.register(ModWoodType.CHERRY_BLOSSOM);

            Regions.register(new ModBiomeProvider());
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
        });

        // TODO: Need to add the boat trade for the new villager type for this to work
        ModVillagerTypes.register();

        ModStructurePieceTypes.register();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("cherryblossomgrotto", "helloworld", () -> { LOGGER.debug("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.debug("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
    }

    /**
     * Inner class for different event registers used by the mod
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /**
         * Used to register blocks into the game using the mod event bus
         *
         * @param registryEvent The register event with which blocks will be registered
         */
        @SubscribeEvent
        public static void onBlocksRegistry(final RegisterEvent registryEvent) {
            registryEvent.register(ForgeRegistries.Keys.BLOCKS, registrar -> {
                // All cherry blossom wood blocks
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_log"), new CherryBlossomLog());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_cherry_blossom_log"), new StrippedCherryBlossomLog());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_wood"), new CherryBlossomWood());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_cherry_blossom_wood"), new StrippedCherryBlossomWood());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_planks"), new CherryBlossomPlanks());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_slab"), new CherryBlossomSlab());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_stairs"), new CherryBlossomStairs());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_button"), new CherryBlossomButton());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_fence"), new CherryBlossomFence());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_fence_gate"), new CherryBlossomFenceGate());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_pressure_plate"), new CherryBlossomPressurePlate());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_sign"), new CherryBlossomSign());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_wall_sign"), new CherryBlossomWallSign());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_hanging_sign"), new CherryBlossomHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_wall_hanging_sign"), new CherryBlossomWallHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_door"), new CherryBlossomDoor());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_trapdoor"), new CherryBlossomTrapDoor());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_planks_trapdoor"), new CherryBlossomPlanksTrapdoor());

                // All cherry blossom tree blocks
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_leaves"), new CherryBlossomLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_petals"), new CherryBlossomPetals());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_sapling"), new CherryBlossomSapling());
                registrar.register(new ResourceLocation(MOD_ID, "potted_cherry_blossom_sapling"), new PottedCherryBlossomSapling());

                // All architectural blocks
                registrar.register(new ResourceLocation(MOD_ID, "shoji_screen"), new ShojiScreen());
                registrar.register(new ResourceLocation(MOD_ID, "tatami_mat"), new TatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "long_tatami_mat"), new LongTatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "aged_tatami_mat"), new AgedTatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "long_aged_tatami_mat"), new LongAgedTatamiMat());

                // All farm crops
                registrar.register(new ResourceLocation(MOD_ID, "rice_paddy"), new RicePaddy());

                // All lanterns
                registrar.register(new ResourceLocation(MOD_ID, "zen_lantern"), new ZenLantern());
                registrar.register(new ResourceLocation(MOD_ID, "soul_zen_lantern"), new SoulZenLantern());

                // All vanilla block trapdoors
                registrar.register(new ResourceLocation(MOD_ID, "stone_trapdoor"), new StoneTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "smooth_stone_trapdoor"), new SmoothStoneTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "cobblestone_trapdoor"), new CobblestoneTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "oak_planks_trapdoor"), new OakPlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "dark_oak_planks_trapdoor"), new DarkOakPlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "acacia_planks_trapdoor"), new AcaciaPlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "jungle_planks_trapdoor"), new JunglePlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "spruce_planks_trapdoor"), new SprucePlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "birch_planks_trapdoor"), new BirchPlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "mangrove_planks_trapdoor"), new MangrovePlanksTrapdoor());
            });
        }

        /**
         * Used to register tile entities into the game using the mod event bus
         * Associated entity tile data is assigned before registration
         *
         * @param registerEvent The register event with which tile entities will be registered
         */
        @SubscribeEvent
        public static void onTileEntityRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, registrar -> {
                // All cherry blossom tree block entities
                registrar.register(new ResourceLocation(MOD_ID, "cherry_leaves_tile_entity"), ModBlockEntities.CHERRY_LEAVES_TYPE);

                // All sign block entities
                registrar.register(new ResourceLocation(MOD_ID, "mod_sign_entity"), ModBlockEntities.SIGN_ENTITY_TYPE);
                registrar.register(new ResourceLocation(MOD_ID, "mod_hanging_sign_entity"), ModBlockEntities.HANGING_SIGN_ENTITY_TYPE);
            });
        }

        /**
         * Used to register items into the game using the mod event bus
         *
         * @param registerEvent The register event with which items will be registered
         */
        @SubscribeEvent
        public static void onItemsRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.ITEMS, registrar -> {
                // All cherry blossom wood items
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_log"), new BlockItem(ModBlocks.CHERRY_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_cherry_blossom_log"), new BlockItem(ModBlocks.STRIPPED_CHERRY_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_wood"), new BlockItem(ModBlocks.CHERRY_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_cherry_blossom_wood"), new BlockItem(ModBlocks.STRIPPED_CHERRY_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_planks"), new BlockItem(ModBlocks.CHERRY_PLANKS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_slab"), new BlockItem(ModBlocks.CHERRY_SLAB, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_stairs"), new BlockItem(ModBlocks.CHERRY_STAIRS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_button"), new BlockItem(ModBlocks.CHERRY_BUTTON, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_fence"), new BlockItem(ModBlocks.CHERRY_FENCE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_fence_gate"), new BlockItem(ModBlocks.CHERRY_FENCE_GATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_pressure_plate"), new BlockItem(ModBlocks.CHERRY_PRESSURE_PLATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_sign"), new SignItem(new Item.Properties().stacksTo(16), ModBlocks.CHERRY_SIGN, ModBlocks.CHERRY_WALL_SIGN));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_hanging_sign"), new HangingSignItem(ModBlocks.CHERRY_HANGING_SIGN, ModBlocks.CHERRY_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_door"), new DoubleHighBlockItem(ModBlocks.CHERRY_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_trapdoor"), new BlockItem(ModBlocks.CHERRY_TRAPDOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_planks_trapdoor"), new BlockItem(ModBlocks.CHERRY_BLOSSOM_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_boat"), new CherryBlossomBoat());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_chest_boat"), new CherryBlossomChestBoat());

                // All cherry blossom tree items
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_leaves"), new BlockItem(ModBlocks.CHERRY_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_petals"), new BlockItem(ModBlocks.CHERRY_PETALS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_petal"), new CherryBlossomPetal());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_sapling"), new BlockItem(ModBlocks.CHERRY_SAPLING, new Item.Properties()));

                // All lantern items
                registrar.register(new ResourceLocation(MOD_ID, "zen_lantern"), new DoubleHighBlockItem(ModBlocks.ZEN_LANTERN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "soul_zen_lantern"), new DoubleHighBlockItem(ModBlocks.SOUL_ZEN_LANTERN, new Item.Properties()));

                // All architectural items
                registrar.register(new ResourceLocation(MOD_ID, "shoji_screen"), new DoubleHighBlockItem(ModBlocks.SHOJI_SCREEN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "tatami_mat"), new BlockItem(ModBlocks.TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "long_tatami_mat"), new BlockItem(ModBlocks.LONG_TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "aged_tatami_mat"), new BlockItem(ModBlocks.AGED_TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "long_aged_tatami_mat"), new BlockItem(ModBlocks.LONG_AGED_TATAMI_MAT, new Item.Properties()));

                // All bucket items
                registrar.register(new ResourceLocation(MOD_ID, "koi_bucket"), new KoiBucket(EntityTypeInitializer.KOI_ENTITY_TYPE));

                // TODO: Find a way to add these to the composter
                // All food items
                registrar.register(new ResourceLocation(MOD_ID, "koi"), new Koi());
                registrar.register(new ResourceLocation(MOD_ID, "cooked_koi"), new CookedKoi());
                registrar.register(new ResourceLocation(MOD_ID, "rice"), new Rice());
                registrar.register(new ResourceLocation(MOD_ID, "onigiri"), new Onigiri());

                // All weapon items
                registrar.register(new ResourceLocation(MOD_ID, "katana"), new Katana());
                registrar.register(new ResourceLocation(MOD_ID, "kunai"), new Kunai());
                registrar.register(new ResourceLocation(MOD_ID, "shuriken"), new Shuriken());

                // All armour items
                registrar.register(new ResourceLocation(MOD_ID, "ninja_mask"), new NinjaMask());
                registrar.register(new ResourceLocation(MOD_ID, "ninja_tunic"), new NinjaTunic());
                registrar.register(new ResourceLocation(MOD_ID, "ninja_leggings"), new NinjaLeggings());
                registrar.register(new ResourceLocation(MOD_ID, "ninja_sandals"), new NinjaSandals());
                registrar.register(new ResourceLocation(MOD_ID, "kabuto_helmet"), new KabutoHelmet());
                registrar.register(new ResourceLocation(MOD_ID, "kabuto_cuirass"), new KabutoCuirass());
                registrar.register(new ResourceLocation(MOD_ID, "kabuto_greaves"), new KabutoGreaves());
                registrar.register(new ResourceLocation(MOD_ID, "kabuto_sandals"), new KabutoSandals());

                // All vanilla block trapdoor items
                registrar.register(new ResourceLocation(MOD_ID, "acacia_planks_trapdoor"), new BlockItem(ModBlocks.ACACIA_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "birch_planks_trapdoor"), new BlockItem(ModBlocks.BIRCH_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "dark_oak_planks_trapdoor"), new BlockItem(ModBlocks.DARK_OAK_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "jungle_planks_trapdoor"), new BlockItem(ModBlocks.JUNGLE_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "oak_planks_trapdoor"), new BlockItem(ModBlocks.OAK_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "spruce_planks_trapdoor"), new BlockItem(ModBlocks.SPRUCE_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "mangrove_planks_trapdoor"), new BlockItem(ModBlocks.MANGROVE_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "smooth_stone_trapdoor"), new BlockItem(ModBlocks.SMOOTH_STONE_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stone_trapdoor"), new BlockItem(ModBlocks.STONE_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cobblestone_trapdoor"), new BlockItem(ModBlocks.COBBLESTONE_TRAP_DOOR, new Item.Properties()));

                // All spawn eggs
                registrar.register(new ResourceLocation(MOD_ID, "koi_spawn_egg"), new ForgeSpawnEggItem(() -> EntityTypeInitializer.KOI_ENTITY_TYPE, 15724527, 16030538, new Item.Properties()));
                // TODO: Select some proper colours or this spawn egg
                registrar.register(new ResourceLocation(MOD_ID, "tanooki_spawn_egg"), new ForgeSpawnEggItem(() -> EntityTypeInitializer.TANOOKI_ENTITY_TYPE, 9738135, 3814711, new Item.Properties()));
            });
        }

        /**
         * Used to attach attribute modifiers to entities using the Forge event bus.
         * Required for living entities.
         *
         * @param event The attachment event with which attribute modifiers will be attached to different entity types
         */
        @SubscribeEvent
        public static void onEntityAttributeRegistration(final EntityAttributeCreationEvent event) {
            event.put(EntityTypeInitializer.KOI_ENTITY_TYPE, AbstractSchoolingFish.createAttributes().build());
            event.put(EntityTypeInitializer.TANOOKI_ENTITY_TYPE, Tanooki.createAttributes().build());
        }

        /**
         * Used to register entities into the game using the mod event bus
         * Associated entity data is assigned before registration
         *
         * @param registerEvent The register event with which entities will be registered
         */
        @SubscribeEvent
        public static void onEntityRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.ENTITY_TYPES, registrar -> {
                // All vehicle entities
                registrar.register(new ResourceLocation(MOD_ID,"mod_boat_entity"), EntityTypeInitializer.BOAT_ENTITY_TYPE);
                registrar.register(new ResourceLocation(MOD_ID,"mod_chest_boat_entity"), EntityTypeInitializer.CHEST_BOAT_ENTITY_TYPE);

                // All weapon entities
                registrar.register(new ResourceLocation(MOD_ID,"kunai_entity"), EntityTypeInitializer.KUNAI_ENTITY_TYPE);
                registrar.register(new ResourceLocation(MOD_ID,"shuriken_entity"), EntityTypeInitializer.SHURIKEN_ENTITY_TYPE);

                // All living entities
                registrar.register(new ResourceLocation(MOD_ID,"koi_entity"), EntityTypeInitializer.KOI_ENTITY_TYPE);
                registrar.register(new ResourceLocation(MOD_ID, "tanooki"), EntityTypeInitializer.TANOOKI_ENTITY_TYPE);
            });
        }

        @SubscribeEvent
        public static void onEntitySpawn(final SpawnPlacementRegisterEvent registerEvent) {
            registerEvent.register(ModEntityType.KOI, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            registerEvent.register(ModEntityType.TANOOKI, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tanooki::checkTanookiSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        }

        /**
         * Used to register features into the game using the mod event bus
         *
         * @param registerEvent The register event with which features will be registered
         */
        @SubscribeEvent
        public static void onFeaturesRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.FEATURES, registrar -> {
                // All cherry blossom tree features
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_petal_ground_cover"), new CherryBlossomPetalCoverFeature());

                // Misc overworld features
                registrar.register(new ResourceLocation(MOD_ID, "hotspring"), new HotspringFeature(HotspringFeature.Configuration.CODEC));
            });

            //TODO: Should I register configured features in here after the feature registration has run? Right now they register at the global level spread across a few new classes and sit in holders. This might not be the right stage to be registering them...
        }

        /**
         * Used to register block state provider types into the game using the mod event bus
         *
         * @param registerEvent The register event with which block state provider types will be registered
         */
        @SubscribeEvent
        public static void onBlockStateProviderTypeRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.BLOCK_STATE_PROVIDER_TYPES, registrar -> {
                // All biome feature blockstate provider types
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_forest_flower_provider"), new CherryBlossomForestFlowerProviderType());
            });
        }

        /**
         * Used to register datapacks into the game using the mod event bus
         *
         * @param gatherEvent The register event with which datapacks will be registered
         */
        @SubscribeEvent
        public static void onGatherDataRegistry(final GatherDataEvent gatherEvent) {
            DataGenerator generator = gatherEvent.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper fileHelper = gatherEvent.getExistingFileHelper();

            HolderLookup.Provider lookupProvider = new RegistrySetBuilder()
                    .add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) ModConfiguredFeatures::bootstrap)
                    .add(Registries.PLACED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) ModPlacements::bootstrap)
                    //.add(Registries.FOLIAGE_PLACER_TYPE, (RegistrySetBuilder.RegistryBootstrap) ModFoliagePlacers::bootstrap)
                    //.add(Registries.PROCESSOR_LIST, ())
                    //.add(Registries.STRUCTURE_SET, ())
                    //.add(Registries.TEMPLATE_POOL, ())
                    .add(Registries.BIOME, ModBiomeInitializer::bootstrap)
                    .buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
        }

        /**
         * Used to register foliage placers into the game using the mod event bus
         *
         * @param registerEvent The registry event with which foliage placers will be registered
         */
        @SubscribeEvent
        public static void onFoliagePlacerRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.FOLIAGE_PLACER_TYPES, registrar -> {
                // All cherry blossom tree foliage placer types
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_tree_foliage_placer"), new CherryBlossomFoliagePlacerType());
                registrar.register(new ResourceLocation(MOD_ID, "big_cherry_blossom_tree_foliage_placer"), new GrandCherryBlossomFoliagePlacerType());

            });
        }

        /**
         * Used to register particle types into the game using the mod event bus
         *
         * @param registerEvent The registry event with which particle types will be registered
         */
        @SubscribeEvent
        public static void onParticleTypeRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.PARTICLE_TYPES, registrar -> {
                // All cherry blossom tree particle types
                registrar.register(new ResourceLocation(MOD_ID, "cherry_blossom_petal"), ModParticles.CHERRY_PETAL);

            });
        }

        /**
         * Used to register capabilities into the game using the mod event bus
         *
         * @param capabilityRegistryEvent The registry event with which capabilities will be registered
         */
        @SubscribeEvent
        public static void onCapabilityRegistration(RegisterCapabilitiesEvent capabilityRegistryEvent) {
            capabilityRegistryEvent.register(DoubleJumpCapability.DoubleJump.class);
        }

        /**
         * Used to register a new custom creative mode tab to the creative mode UI using the mod event bus
         *
         * @param creativeTabBuildRegistryEvent The registry event with which new creative mode tabs are created and populated with items
         */
        @SubscribeEvent
        public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register creativeTabBuildRegistryEvent) {
            creativeTabBuildRegistryEvent.registerCreativeModeTab(new ResourceLocation(MOD_ID, "cherry_blossom_grotto_creative_tab"), builder ->
                builder.title(Component.translatable("Cherry Blossom Grotto"))
                    .icon(() -> new ItemStack(ModItems.CHERRY_SAPLING))
                    .displayItems((enabledFlags, populator, hasPermissions) -> {
                        // Cherry blossom blocks
                        populator.accept(ModItems.CHERRY_LOG);
                        populator.accept(ModItems.CHERRY_WOOD);
                        populator.accept(ModItems.STRIPPED_CHERRY_LOG);
                        populator.accept(ModItems.STRIPPED_CHERRY_WOOD);
                        populator.accept(ModItems.CHERRY_PLANKS);
                        populator.accept(ModItems.CHERRY_STAIRS);
                        populator.accept(ModItems.CHERRY_SLAB);
                        populator.accept(ModItems.CHERRY_FENCE);
                        populator.accept(ModItems.CHERRY_FENCE_GATE);
                        populator.accept(ModItems.CHERRY_DOOR);
                        populator.accept(ModItems.CHERRY_TRAPDOOR);
                        populator.accept(ModItems.CHERRY_PRESSURE_PLATE);
                        populator.accept(ModItems.CHERRY_LOG);
                        populator.accept(ModItems.CHERRY_BUTTON);
                        populator.accept(ModItems.CHERRY_LEAVES);
                        populator.accept(ModItems.CHERRY_SAPLING);
                        populator.accept(new ItemStack(ModItems.CHERRY_PETAL));
                        populator.accept(ModItems.CHERRY_PETALS);
                        populator.accept(new ItemStack(ModItems.CHERRY_BOAT));
                        populator.accept(new ItemStack(ModItems.CHERRY_CHEST_BOAT));
                        populator.accept(ModItems.CHERRY_SIGN);
                        populator.accept(ModItems.CHERRY_HANGING_SIGN);

                        // Crops
                        populator.accept(new ItemStack(ModItems.RICE));
                        populator.accept(new ItemStack(ModItems.ONIGIRI));

                        // Wildlife
                        populator.accept(new ItemStack(ModItems.KOI));
                        populator.accept(new ItemStack(ModItems.COOKED_KOI));
                        populator.accept(new ItemStack(ModItems.KOI_BUCKET));
                        populator.accept(new ItemStack(ModItems.KOI_SPAWN_EGG));
                        populator.accept(new ItemStack(ModItems.TANOOKI_SPAWN_EGG));

                        // Misc building blocks
                        populator.accept(ModItems.SHOJI_SCREEN);
                        populator.accept(ModItems.TATAMI_MAT);
                        populator.accept(ModItems.LONG_TATAMI_MAT);
                        populator.accept(ModItems.AGED_TATAMI_MAT);
                        populator.accept(ModItems.LONG_AGED_TATAMI_MAT);
                        populator.accept(ModItems.ZEN_LANTERN);
                        populator.accept(ModItems.SOUL_ZEN_LANTERN);

                        // Weapons & Armour
                        populator.accept(new ItemStack(ModItems.KATANA));
                        populator.accept(new ItemStack(ModItems.KUNAI));
                        populator.accept(new ItemStack(ModItems.SHURIKEN));
                        populator.accept(new ItemStack(ModItems.NINJA_MASK));
                        populator.accept(new ItemStack(ModItems.NINJA_TUNIC));
                        populator.accept(new ItemStack(ModItems.NINJA_LEGGINGS));
                        populator.accept(new ItemStack(ModItems.NINJA_SANDALS));
                        populator.accept(new ItemStack(ModItems.KABUTO_HELMET));
                        populator.accept(new ItemStack(ModItems.KABUTO_CUIRASS));
                        populator.accept(new ItemStack(ModItems.KABUTO_GREAVES));
                        populator.accept(new ItemStack(ModItems.KABUTO_SANDALS));

                        // Hidden trapdoors
                        populator.accept(ModItems.CHERRY_BLOSSOM_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.ACACIA_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.BIRCH_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.DARK_OAK_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.JUNGLE_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.OAK_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.SPRUCE_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.MANGROVE_PLANKS_TRAP_DOOR);
                        populator.accept(ModItems.SMOOTH_STONE_TRAP_DOOR);
                        populator.accept(ModItems.STONE_TRAP_DOOR);
                        populator.accept(ModItems.SMOOTH_STONE_TRAP_DOOR);
                        populator.accept(ModItems.COBBLESTONE_TRAP_DOOR);
                    }
                )
            );
        }

        /**
         * Used to register items into vanilla creative mode tabs in the creative mode UI using the mod event bus
         *
         * @param creativeTabBuilderRegistryEvent The registry event with which new items are added to vanilla creative mode tabs
         */
        @SubscribeEvent
        public static void onCreativeModeTabBuilderRegister(CreativeModeTabEvent.BuildContents creativeTabBuilderRegistryEvent) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = creativeTabBuilderRegistryEvent.getEntries();
            CreativeModeTab.TabVisibility visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

            if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                // Wood blocks
                entries.putAfter(new ItemStack(Items.MANGROVE_BUTTON), new ItemStack(ModItems.CHERRY_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_LOG), new ItemStack(ModItems.CHERRY_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_WOOD), new ItemStack(ModItems.STRIPPED_CHERRY_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_CHERRY_LOG), new ItemStack(ModItems.STRIPPED_CHERRY_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_CHERRY_WOOD), new ItemStack(ModItems.CHERRY_PLANKS), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_PLANKS), new ItemStack(ModItems.CHERRY_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_STAIRS), new ItemStack(ModItems.CHERRY_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_SLAB), new ItemStack(ModItems.CHERRY_FENCE), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_FENCE), new ItemStack(ModItems.CHERRY_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_FENCE_GATE), new ItemStack(ModItems.CHERRY_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_DOOR), new ItemStack(ModItems.CHERRY_TRAPDOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_TRAPDOOR), new ItemStack(ModItems.CHERRY_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_PRESSURE_PLATE), new ItemStack(ModItems.CHERRY_BUTTON), visibility);

                // Misc building blocks
                creativeTabBuilderRegistryEvent.accept(ModItems.SHOJI_SCREEN);
                creativeTabBuilderRegistryEvent.accept(ModItems.TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.LONG_TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.AGED_TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.LONG_AGED_TATAMI_MAT);

                // Hidden trapdoors
                entries.putAfter(new ItemStack(Items.ACACIA_TRAPDOOR), new ItemStack(ModItems.ACACIA_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.ACACIA_TRAPDOOR), new ItemStack(ModItems.BIRCH_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.DARK_OAK_TRAPDOOR), new ItemStack(ModItems.DARK_OAK_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.JUNGLE_TRAPDOOR), new ItemStack(ModItems.JUNGLE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.OAK_TRAPDOOR), new ItemStack(ModItems.OAK_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.SPRUCE_TRAPDOOR), new ItemStack(ModItems.SPRUCE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.MANGROVE_TRAPDOOR), new ItemStack(ModItems.MANGROVE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_TRAPDOOR), new ItemStack(ModItems.CHERRY_BLOSSOM_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.STONE_SLAB), new ItemStack(ModItems.STONE_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.SMOOTH_STONE_SLAB), new ItemStack(ModItems.SMOOTH_STONE_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.COBBLESTONE_WALL), new ItemStack(ModItems.COBBLESTONE_TRAP_DOOR), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
                // Cherry blossom blocks
                entries.putAfter(new ItemStack(Items.MANGROVE_LOG), new ItemStack(ModItems.CHERRY_LOG), visibility);
                entries.putAfter(new ItemStack(Items.FLOWERING_AZALEA_LEAVES), new ItemStack(ModItems.CHERRY_LEAVES), visibility);
                entries.putAfter(new ItemStack(Items.FLOWERING_AZALEA), new ItemStack(ModItems.CHERRY_SAPLING), visibility);
                entries.putAfter(new ItemStack(Items.SWEET_BERRIES), new ItemStack(ModItems.RICE), visibility);
                entries.putAfter(new ItemStack(ModItems.RICE), new ItemStack(ModItems.CHERRY_PETAL), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_PETAL), new ItemStack(ModItems.CHERRY_PETALS), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                // Lanterns
                entries.putAfter(new ItemStack(Items.SOUL_LANTERN), new ItemStack(ModItems.ZEN_LANTERN), visibility);
                entries.putAfter(new ItemStack(ModItems.ZEN_LANTERN), new ItemStack(ModItems.SOUL_ZEN_LANTERN), visibility);

                // Signs
                entries.putAfter(new ItemStack(Items.MANGROVE_SIGN), new ItemStack(ModItems.CHERRY_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_SIGN), new ItemStack(ModItems.CHERRY_HANGING_SIGN), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
                // Cherry blossom blocks
                entries.putAfter(new ItemStack(Items.OAK_BUTTON), new ItemStack(ModItems.CHERRY_BUTTON), visibility);
                entries.putAfter(new ItemStack(Items.OAK_PRESSURE_PLATE), new ItemStack(ModItems.CHERRY_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(Items.OAK_CHEST_BOAT), new ItemStack(ModItems.CHERRY_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(Items.OAK_FENCE_GATE), new ItemStack(ModItems.CHERRY_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(Items.OAK_DOOR), new ItemStack(ModItems.CHERRY_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.OAK_TRAPDOOR), new ItemStack(ModItems.CHERRY_TRAPDOOR), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                // Buckets
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH_BUCKET), new ItemStack(ModItems.KOI_BUCKET), visibility);

                // Boats
                entries.putAfter(new ItemStack(Items.MANGROVE_CHEST_BOAT), new ItemStack(ModItems.CHERRY_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.CHERRY_BOAT), new ItemStack(ModItems.CHERRY_CHEST_BOAT), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.COMBAT) {
                // Melee weapons
                entries.putAfter(new ItemStack(Items.NETHERITE_AXE), new ItemStack(ModItems.KATANA), visibility);

                // Armours
                entries.putAfter(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(ModItems.NINJA_MASK), visibility);
                entries.putAfter(new ItemStack(ModItems.NINJA_MASK), new ItemStack(ModItems.NINJA_TUNIC), visibility);
                entries.putAfter(new ItemStack(ModItems.NINJA_TUNIC), new ItemStack(ModItems.NINJA_LEGGINGS), visibility);
                entries.putAfter(new ItemStack(ModItems.NINJA_LEGGINGS), new ItemStack(ModItems.NINJA_SANDALS), visibility);
                entries.putAfter(new ItemStack(Items.IRON_BOOTS), new ItemStack(ModItems.KABUTO_HELMET), visibility);
                entries.putAfter(new ItemStack(ModItems.KABUTO_HELMET), new ItemStack(ModItems.KABUTO_CUIRASS), visibility);
                entries.putAfter(new ItemStack(ModItems.KABUTO_CUIRASS), new ItemStack(ModItems.KABUTO_GREAVES), visibility);
                entries.putAfter(new ItemStack(ModItems.KABUTO_GREAVES), new ItemStack(ModItems.KABUTO_SANDALS), visibility);

                // Throwables
                creativeTabBuilderRegistryEvent.accept(new ItemStack(ModItems.KUNAI));
                creativeTabBuilderRegistryEvent.accept(new ItemStack(ModItems.SHURIKEN));
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
                // Crops
                entries.putAfter(new ItemStack(Items.BEETROOT), new ItemStack(ModItems.RICE), visibility);
                entries.putAfter(new ItemStack(ModItems.RICE), new ItemStack(ModItems.ONIGIRI), visibility);

                // Fish
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH), new ItemStack(ModItems.KOI), visibility);
                entries.putAfter(new ItemStack(ModItems.KOI), new ItemStack(ModItems.COOKED_KOI), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.INGREDIENTS) {
                // Plants
                entries.putAfter(new ItemStack(Items.WHEAT), new ItemStack(ModItems.CHERRY_PETAL), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTab() == CreativeModeTabs.SPAWN_EGGS) {
                // Fish
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH_SPAWN_EGG), new ItemStack(ModItems.KOI_SPAWN_EGG), visibility);

                // Animals
                // TODO: Put ater whatever is the last animal spawn egg in the overworld
                entries.putAfter(new ItemStack(Items.COW_SPAWN_EGG), new ItemStack(ModItems.TANOOKI_SPAWN_EGG), visibility);
            }
        }
    }

    /**
     * Inner class for different event handlers overriding handlers from vanilla Minecraft
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        /**
         * Used to attach modded capabilities to entities using the Forge event bus
         *
         * @param event The attachment event with which capabilities will be attached to different entity types
         */
        @SubscribeEvent
        public static void onEntityCapabilityRegistration(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(DoubleJumpCapability.DOUBLE_JUMP).isPresent()) {
                    event.addCapability(new ResourceLocation(MOD_ID,"double_jump"), new DoubleJumpCapability());
                }
            }
        }

        /**
         * Used to handle changes to item attribute modifiers on vanilla items.
         * Called whenever a player equips/unequips an item or whenever an item's tooltip is being renderred.
         *
         * @param event The event object that is built when an item needs to check its attribute modifiers
         */
        @SubscribeEvent
        public static void itemAttributeModifier(ItemAttributeModifierEvent event) {
        }

        /**
         * Used to handle events that occur when a block is placed into the world.
         * Currently this handles the replacement of base game grass blocks with our modded variant.
         * We also check to see if we should replace the block below the current so that we stop issues
         * with blocks placed on top of grass reverting the block back to the vanilla variant.
         *
         * @param event The event object that is build when a block is placed
         */
        @SubscribeEvent
        public static void onPlace(BlockEvent.EntityPlaceEvent event) {
            //ModBlocks.GRASS.replaceVanillaGrassBlock(event.getPos(),  (World) event.getWorld());

            // NOTE: This fix to the grass being placed on makes the ground incompatible with tools for some reason. Like the game still thinks its covered ground maybe??
            //ModBlocks.GRASS.replaceVanillaGrassBlock(event.getPos().below(),  (World) event.getWorld());
        }

        /**
         * Used to handle events that notify neighbouring blocks of changes.
         * Currently this handles the replacement of base game grass blocks with our modded variant
         * whenever a grass propegation occurs since the random ticker in the new block does not seem to be
         * sufficient alone.
         *
         * @param event The event object that is built when a block is updated
         */
        @SubscribeEvent
        public static void onNeighbourNotified(BlockEvent.NeighborNotifyEvent event) {
            //ModBlocks.GRASS.replaceVanillaGrassBlock(event.getPos(), (World) event.getWorld());
        }

        /**
         * Used to handle events that occur when a block is right-clicked by a player.
         * Currently this handles the stripping that occurs with new wood-based blocks that are right-clicked with an axe
         *
         * @param event The event object that is built when a block is right-clicked by a player
         */
        @SubscribeEvent
        public static void onBlockClicked(PlayerInteractEvent.RightClickBlock event) {
            final Map<Block, Block> BLOCK_STRIPPING_MAP = (new ImmutableMap.Builder<Block, Block>().put(ModBlocks.CHERRY_LOG, ModBlocks.STRIPPED_CHERRY_LOG).put(ModBlocks.CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD)).build();

            if (event.getItemStack().getItem() instanceof AxeItem) {
                net.minecraft.world.level.Level level = event.getLevel();
                BlockPos position = event.getPos();
                BlockState state = level.getBlockState(position);
                Block block = BLOCK_STRIPPING_MAP.get(state.getBlock());
                if (block != null) {
                    Player player = event.getEntity();
                    level.playSound(player, position, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(position, block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
                    event.getItemStack().hurtAndBreak(1, player, (p_220036_0_) -> p_220036_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }

        /**
         * Handles any custom logic that is needed on players on any given tick.
         *
         * Currently this checks if the player has tried to perform a double jump and communicates that action
         * to the server and performs all necessary updates to the player.
         *
         * @param event The tick event for a given player
         */
        @SubscribeEvent
        public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Player player = event.player;
                boolean isJumping = (boolean) ForgeReflection.getObfuscatedPrivatizedFieldValue(LivingEntity.class, "f_20899_", player);
                int jumpCooldownTimer = (int) ForgeReflection.getObfuscatedPrivatizedFieldValue(LivingEntity.class, "f_20954_", player);
                if (isJumping && jumpCooldownTimer <= 0) {

                    if (!player.isPassenger() && !player.isFallFlying() && !player.isInWater() && !player.isInLava() && !player.isSleeping() && !player.isSwimming() && !player.isDeadOrDying()) {
                        if (player.getFoodData().getFoodLevel() > 3 && EnchantmentHelper.getEnchantmentLevel(DOUBLE_JUMP_ENCHANTMENT.get(), player) > 0) {
                            DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DoubleJumpCapability.DOUBLE_JUMP).orElse(null);
                            if (doubleJumpCapability != null) {
                                if (!doubleJumpCapability.hasDoubleJumped()) {
                                    player.jumpFromGround();
                                    player.fallDistance = 0.0F;
                                    player.causeFoodExhaustion(player.isSprinting() ? 0.2F * 3.0F : 0.05F * 3.0F);
                                    player.noJumpDelay = 10;

                                    DoubleJumpServerMessage message = new DoubleJumpServerMessage(true);
                                    Main.NETWORK_CHANNEL.sendToServer(message);
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Handles any custom logic that needs to happen whenever a living entity falls and hits the ground.
         *
         * This resets the double jump state of the given entity.
         *
         * @param event The event triggered by a living entity falling and hitting the ground.
         */
        @SubscribeEvent
        public static void onPlayerFall(final LivingFallEvent event) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DoubleJumpCapability.DOUBLE_JUMP).orElse(null);
                if (doubleJumpCapability != null) {
                    doubleJumpCapability.setHasDoubleJumped(false);
                }
            }
        }

        /**
         * Handles any custom logic that needs to happen when an entity joins the current world.
         *
         * This attempts to communicate the saved NBT state on a player's double jump capability back onto to
         * the player so that they can't reset their double jumping state by reconnecting to the server.
         *
         * @param event The event triggered by an entity joining the world
         */
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onEntityJoinWorld(final EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                boolean hasDoubleJumped = player.getCapability(DoubleJumpCapability.DOUBLE_JUMP).map(DoubleJumpCapability.IDoubleJump::hasDoubleJumped).orElse(false);
                if (hasDoubleJumped) {
                    DoubleJumpClientMessage message = new DoubleJumpClientMessage(player.getUUID(), true);
                    Main.NETWORK_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
                }
            }
        }
    }
}
