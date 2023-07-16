package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.client.network.handlers.DoubleJumpClientMessageHandler;
import com.deku.cherryblossomgrotto.client.network.messages.DoubleJumpClientMessage;
import com.deku.cherryblossomgrotto.common.blocks.*;
import com.deku.cherryblossomgrotto.common.blocks.black_pine.*;
import com.deku.cherryblossomgrotto.common.blocks.hinoki.*;
import com.deku.cherryblossomgrotto.common.blocks.maple.*;
import com.deku.cherryblossomgrotto.common.blocks.water_fir.*;
import com.deku.cherryblossomgrotto.common.capabilities.DoubleJumpCapability;
import com.deku.cherryblossomgrotto.common.enchantments.ModEnchantmentInitializer;
import com.deku.cherryblossomgrotto.common.entity.ModEntityTypeInitializer;
import com.deku.cherryblossomgrotto.common.entity.ModBlockEntities;
import com.deku.cherryblossomgrotto.common.entity.ai.memory.ModMemoryModuleTypes;
import com.deku.cherryblossomgrotto.common.entity.ai.sensing.ModSensorTypes;
import com.deku.cherryblossomgrotto.common.entity.animal.tanooki.Tanooki;
import com.deku.cherryblossomgrotto.common.entity.monster.terracotta_warrior.TerracottaWarrior;
import com.deku.cherryblossomgrotto.common.entity.npc.ModVillagerTypes;
import com.deku.cherryblossomgrotto.common.features.*;
import com.deku.cherryblossomgrotto.common.features.decorators.ModTreeDecoratorTypes;
import com.deku.cherryblossomgrotto.common.items.*;
import com.deku.cherryblossomgrotto.common.items.black_pine.BlackPineBoat;
import com.deku.cherryblossomgrotto.common.items.black_pine.BlackPineChestBoat;
import com.deku.cherryblossomgrotto.common.items.hinoki.HinokiBoat;
import com.deku.cherryblossomgrotto.common.items.hinoki.HinokiChestBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleChestBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleLeaf;
import com.deku.cherryblossomgrotto.common.items.maple.MapleSyrupBottleItem;
import com.deku.cherryblossomgrotto.common.items.water_fir.WaterFirBoat;
import com.deku.cherryblossomgrotto.common.items.water_fir.WaterFirChestBoat;
import com.deku.cherryblossomgrotto.common.particles.ModParticles;
import com.deku.cherryblossomgrotto.common.recipes.ModRecipeSerializerInitializer;
import com.deku.cherryblossomgrotto.common.sounds.ModSoundEvents;
import com.deku.cherryblossomgrotto.common.ui.ModCreativeTabs;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeProvider;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModSurfaceRules;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.BlackPineFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.structures.*;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.*;
import com.deku.cherryblossomgrotto.server.network.handlers.DoubleJumpServerMessageHandler;
import com.deku.cherryblossomgrotto.server.network.messages.DoubleJumpServerMessage;
import com.deku.cherryblossomgrotto.utils.LogTweaker;
import com.deku.cherryblossomgrotto.utils.ModConfiguration;
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
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.api.distmarker.Dist;
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
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfiguration.COMMON_SPEC, "cherryblossomgrotto-common.toml");

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

        // Entity Types logic
        ModEntityTypeInitializer.ENTITY_TYPES.register(eventBus);

        // Item logic
        ModItemInitializer.ITEMS.register(eventBus);

        // Tree Decorator Types
        ModTreeDecoratorTypes.TREE_DECORATOR_TYPES.register(eventBus);

        // Trunk Placer Types
        ModTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(eventBus);

        // AI Sensor Types
        ModSensorTypes.SENSOR_TYPES.register(eventBus);

        // AI Memory Module Types
        ModMemoryModuleTypes.MEMORY_MODULE_TYPES.register(eventBus);

        // Custom recipe serializers
        ModRecipeSerializerInitializer.RECIPE_SERIALIZERS.register(eventBus);

        // Creative Mode Tabs
        ModCreativeTabs.CREATIVE_MOD_TABS.register(eventBus);

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
            BlockSetType.register(ModBlockSetType.MAPLE);
            BlockSetType.register(ModBlockSetType.BLACK_PINE);
            BlockSetType.register(ModBlockSetType.HINOKI);
            BlockSetType.register(ModBlockSetType.WATER_FIR);

            WoodType.register(ModWoodType.MAPLE);
            WoodType.register(ModWoodType.BLACK_PINE);
            WoodType.register(ModWoodType.HINOKI);
            WoodType.register(ModWoodType.WATER_FIR);

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
                // All maple wood blocks
                registrar.register(new ResourceLocation(MOD_ID, "maple_log"), new MapleLog());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_maple_log"), new StrippedMapleLog());
                registrar.register(new ResourceLocation(MOD_ID, "maple_wood"), new MapleWood());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_maple_wood"), new StrippedMapleWood());
                registrar.register(new ResourceLocation(MOD_ID, "maple_planks"), new MaplePlanks());
                registrar.register(new ResourceLocation(MOD_ID, "maple_slab"), new MapleSlab());
                registrar.register(new ResourceLocation(MOD_ID, "maple_stairs"), new MapleStairs());
                registrar.register(new ResourceLocation(MOD_ID, "maple_button"), new MapleButton());
                registrar.register(new ResourceLocation(MOD_ID, "maple_fence"), new MapleFence());
                registrar.register(new ResourceLocation(MOD_ID, "maple_fence_gate"), new MapleFenceGate());
                registrar.register(new ResourceLocation(MOD_ID, "maple_pressure_plate"), new MaplePressurePlate());
                registrar.register(new ResourceLocation(MOD_ID, "maple_sign"), new MapleSign());
                registrar.register(new ResourceLocation(MOD_ID, "maple_wall_sign"), new MapleWallSign());
                registrar.register(new ResourceLocation(MOD_ID, "maple_hanging_sign"), new MapleHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "maple_wall_hanging_sign"), new MapleWallHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "maple_door"), new MapleDoor());
                registrar.register(new ResourceLocation(MOD_ID, "maple_trapdoor"), new MapleTrapDoor());
                registrar.register(new ResourceLocation(MOD_ID, "maple_planks_trapdoor"), new MaplePlanksTrapdoor());

                // All maple tree blocks
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaves"), new MapleLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaf_pile"), new MapleLeafPile());
                registrar.register(new ResourceLocation(MOD_ID, "maple_sapling"), new MapleSapling());
                registrar.register(new ResourceLocation(MOD_ID, "potted_maple_sapling"), new PottedMapleSapling());

                // All black pine wood blocks
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_log"), new BlackPineLog());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_black_pine_log"), new StrippedBlackPineLog());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_wood"), new BlackPineWood());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_black_pine_wood"), new StrippedBlackPineWood());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_planks"), new BlackPinePlanks());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_slab"), new BlackPineSlab());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_stairs"), new BlackPineStairs());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_button"), new BlackPineButton());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_fence"), new BlackPineFence());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_fence_gate"), new BlackPineFenceGate());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_pressure_plate"), new BlackPinePressurePlate());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_sign"), new BlackPineSign());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_wall_sign"), new BlackPineWallSign());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_hanging_sign"), new BlackPineHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_wall_hanging_sign"), new BlackPineWallHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_door"), new BlackPineDoor());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_trapdoor"), new BlackPineTrapDoor());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_planks_trapdoor"), new BlackPinePlanksTrapdoor());

                // All black pine tree blocks
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_leaves"), new BlackPineLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_sapling"), new BlackPineSapling());
                registrar.register(new ResourceLocation(MOD_ID, "potted_black_pine_sapling"), new PottedBlackPineSapling());

                // All hinoki wood blocks
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_log"), new HinokiLog());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_hinoki_log"), new StrippedHinokiLog());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_wood"), new HinokiWood());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_hinoki_wood"), new StrippedHinokiWood());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_planks"), new HinokiPlanks());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_slab"), new HinokiSlab());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_stairs"), new HinokiStairs());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_button"), new HinokiButton());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_fence"), new HinokiFence());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_fence_gate"), new HinokiFenceGate());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_pressure_plate"), new HinokiPressurePlate());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_sign"), new HinokiSign());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_wall_sign"), new HinokiWallSign());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_hanging_sign"), new HinokiHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_wall_hanging_sign"), new HinokiWallHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_door"), new HinokiDoor());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_trapdoor"), new HinokiTrapDoor());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_planks_trapdoor"), new HinokiPlanksTrapdoor());

                // All hinoki tree blocks
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_leaves"), new HinokiLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_sapling"), new HinokiSapling());
                registrar.register(new ResourceLocation(MOD_ID, "potted_hinoki_sapling"), new PottedHinokiSapling());

                // All water fir wood blocks
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_log"), new WaterFirLog());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_water_fir_log"), new StrippedWaterFirLog());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_wood"), new WaterFirWood());
                registrar.register(new ResourceLocation(MOD_ID, "stripped_water_fir_wood"), new StrippedWaterFirWood());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_planks"), new WaterFirPlanks());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_slab"), new WaterFirSlab());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_stairs"), new WaterFirStairs());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_button"), new WaterFirButton());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_fence"), new WaterFirFence());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_fence_gate"), new WaterFirFenceGate());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_pressure_plate"), new WaterFirPressurePlate());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_sign"), new WaterFirSign());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_wall_sign"), new WaterFirWallSign());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_hanging_sign"), new WaterFirHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_wall_hanging_sign"), new WaterFirWallHangingSign());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_door"), new WaterFirDoor());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_trapdoor"), new WaterFirTrapDoor());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_planks_trapdoor"), new WaterFirPlanksTrapdoor());

                // All water fir tree blocks
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_leaves"), new WaterFirLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "autumnal_water_fir_leaves"), new AutumnalWaterFirLeaves());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_sapling"), new WaterFirSapling());
                registrar.register(new ResourceLocation(MOD_ID, "potted_water_fir_sapling"), new PottedWaterFirSapling());

                // All architectural blocks
                registrar.register(new ResourceLocation(MOD_ID, "shoji_screen"), new ShojiScreen());
                registrar.register(new ResourceLocation(MOD_ID, "dark_shoji_screen"), new ShojiScreen());
                registrar.register(new ResourceLocation(MOD_ID, "tatami_mat"), new TatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "long_tatami_mat"), new LongTatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "aged_tatami_mat"), new AgedTatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "long_aged_tatami_mat"), new LongAgedTatamiMat());
                registrar.register(new ResourceLocation(MOD_ID, "terracotta_warrior_statue"), new TerracottaWarriorStatue());
                registrar.register(new ResourceLocation(MOD_ID, "red_fence"), new RedFence());

                // All farm crops
                registrar.register(new ResourceLocation(MOD_ID, "rice_paddy"), new RicePaddy());

                // Mushrooms
                registrar.register(new ResourceLocation(MOD_ID, "enoki_mushroom"), new EnokiMushroom());
                registrar.register(new ResourceLocation(MOD_ID, "shiitake_mushroom"), new ShiitakeMushroom());
                registrar.register(new ResourceLocation(MOD_ID, "enoki_mushroom_block"), new EnokiMushroomBlock());

                // All lanterns
                registrar.register(new ResourceLocation(MOD_ID, "zen_lantern"), new ZenLantern());
                registrar.register(new ResourceLocation(MOD_ID, "soul_zen_lantern"), new SoulZenLantern());
                registrar.register(new ResourceLocation(MOD_ID, "paper_lantern"), new PaperLantern());

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
                registrar.register(new ResourceLocation(MOD_ID, "bamboo_planks_trapdoor"), new BambooPlanksTrapdoor());
                registrar.register(new ResourceLocation(MOD_ID, "cherry_planks_trapdoor"), new CherryPlanksTrapdoor());
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
                // All tree block entities
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaves_tile_entity"), ModBlockEntities.MAPLE_LEAVES_TYPE);

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
                // All maple wood items
                registrar.register(new ResourceLocation(MOD_ID, "maple_log"), new BlockItem(ModBlocks.MAPLE_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_maple_log"), new BlockItem(ModBlocks.STRIPPED_MAPLE_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_wood"), new BlockItem(ModBlocks.MAPLE_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_maple_wood"), new BlockItem(ModBlocks.STRIPPED_MAPLE_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_planks"), new BlockItem(ModBlocks.MAPLE_PLANKS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_slab"), new BlockItem(ModBlocks.MAPLE_SLAB, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_stairs"), new BlockItem(ModBlocks.MAPLE_STAIRS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_button"), new BlockItem(ModBlocks.MAPLE_BUTTON, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_fence"), new BlockItem(ModBlocks.MAPLE_FENCE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_fence_gate"), new BlockItem(ModBlocks.MAPLE_FENCE_GATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_pressure_plate"), new BlockItem(ModBlocks.MAPLE_PRESSURE_PLATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_sign"), new SignItem(new Item.Properties().stacksTo(16), ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN));
                registrar.register(new ResourceLocation(MOD_ID, "maple_hanging_sign"), new HangingSignItem(ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
                registrar.register(new ResourceLocation(MOD_ID, "maple_door"), new DoubleHighBlockItem(ModBlocks.MAPLE_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_trapdoor"), new BlockItem(ModBlocks.MAPLE_TRAPDOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_planks_trapdoor"), new BlockItem(ModBlocks.MAPLE_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_boat"), new MapleBoat());
                registrar.register(new ResourceLocation(MOD_ID, "maple_chest_boat"), new MapleChestBoat());

                // All maple tree items
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaves"), new BlockItem(ModBlocks.MAPLE_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaf_pile"), new BlockItem(ModBlocks.MAPLE_LEAF_PILE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaf"), new MapleLeaf());
                registrar.register(new ResourceLocation(MOD_ID, "maple_sapling"), new BlockItem(ModBlocks.MAPLE_SAPLING, new Item.Properties()));

                // All black pine wood items
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_log"), new BlockItem(ModBlocks.BLACK_PINE_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_black_pine_log"), new BlockItem(ModBlocks.STRIPPED_BLACK_PINE_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_wood"), new BlockItem(ModBlocks.BLACK_PINE_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_black_pine_wood"), new BlockItem(ModBlocks.STRIPPED_BLACK_PINE_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_planks"), new BlockItem(ModBlocks.BLACK_PINE_PLANKS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_slab"), new BlockItem(ModBlocks.BLACK_PINE_SLAB, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_stairs"), new BlockItem(ModBlocks.BLACK_PINE_STAIRS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_button"), new BlockItem(ModBlocks.BLACK_PINE_BUTTON, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_fence"), new BlockItem(ModBlocks.BLACK_PINE_FENCE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_fence_gate"), new BlockItem(ModBlocks.BLACK_PINE_FENCE_GATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_pressure_plate"), new BlockItem(ModBlocks.BLACK_PINE_PRESSURE_PLATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_sign"), new SignItem(new Item.Properties().stacksTo(16), ModBlocks.BLACK_PINE_SIGN, ModBlocks.BLACK_PINE_WALL_SIGN));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_hanging_sign"), new HangingSignItem(ModBlocks.BLACK_PINE_HANGING_SIGN, ModBlocks.BLACK_PINE_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_door"), new DoubleHighBlockItem(ModBlocks.BLACK_PINE_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_trapdoor"), new BlockItem(ModBlocks.BLACK_PINE_TRAPDOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_planks_trapdoor"), new BlockItem(ModBlocks.BLACK_PINE_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_boat"), new BlackPineBoat());
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_chest_boat"), new BlackPineChestBoat());

                // All black pine tree items
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_leaves"), new BlockItem(ModBlocks.BLACK_PINE_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_sapling"), new BlockItem(ModBlocks.BLACK_PINE_SAPLING, new Item.Properties()));

                // All hinoki wood items
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_log"), new BlockItem(ModBlocks.HINOKI_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_hinoki_log"), new BlockItem(ModBlocks.STRIPPED_HINOKI_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_wood"), new BlockItem(ModBlocks.HINOKI_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_hinoki_wood"), new BlockItem(ModBlocks.STRIPPED_HINOKI_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_planks"), new BlockItem(ModBlocks.HINOKI_PLANKS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_slab"), new BlockItem(ModBlocks.HINOKI_SLAB, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_stairs"), new BlockItem(ModBlocks.HINOKI_STAIRS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_button"), new BlockItem(ModBlocks.HINOKI_BUTTON, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_fence"), new BlockItem(ModBlocks.HINOKI_FENCE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_fence_gate"), new BlockItem(ModBlocks.HINOKI_FENCE_GATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_pressure_plate"), new BlockItem(ModBlocks.HINOKI_PRESSURE_PLATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_sign"), new SignItem(new Item.Properties().stacksTo(16), ModBlocks.HINOKI_SIGN, ModBlocks.HINOKI_WALL_SIGN));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_hanging_sign"), new HangingSignItem(ModBlocks.HINOKI_HANGING_SIGN, ModBlocks.HINOKI_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_door"), new DoubleHighBlockItem(ModBlocks.HINOKI_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_trapdoor"), new BlockItem(ModBlocks.HINOKI_TRAPDOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_planks_trapdoor"), new BlockItem(ModBlocks.HINOKI_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_boat"), new HinokiBoat());
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_chest_boat"), new HinokiChestBoat());

                // All hinoki tree items
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_leaves"), new BlockItem(ModBlocks.HINOKI_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "hinoki_sapling"), new BlockItem(ModBlocks.HINOKI_SAPLING, new Item.Properties()));

                // All water fir wood items
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_log"), new BlockItem(ModBlocks.WATER_FIR_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_water_fir_log"), new BlockItem(ModBlocks.STRIPPED_WATER_FIR_LOG, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_wood"), new BlockItem(ModBlocks.WATER_FIR_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stripped_water_fir_wood"), new BlockItem(ModBlocks.STRIPPED_WATER_FIR_WOOD, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_planks"), new BlockItem(ModBlocks.WATER_FIR_PLANKS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_slab"), new BlockItem(ModBlocks.WATER_FIR_SLAB, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_stairs"), new BlockItem(ModBlocks.WATER_FIR_STAIRS, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_button"), new BlockItem(ModBlocks.WATER_FIR_BUTTON, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_fence"), new BlockItem(ModBlocks.WATER_FIR_FENCE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_fence_gate"), new BlockItem(ModBlocks.WATER_FIR_FENCE_GATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_pressure_plate"), new BlockItem(ModBlocks.WATER_FIR_PRESSURE_PLATE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_sign"), new SignItem(new Item.Properties().stacksTo(16), ModBlocks.WATER_FIR_SIGN, ModBlocks.WATER_FIR_WALL_SIGN));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_hanging_sign"), new HangingSignItem(ModBlocks.WATER_FIR_HANGING_SIGN, ModBlocks.WATER_FIR_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_door"), new DoubleHighBlockItem(ModBlocks.WATER_FIR_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_trapdoor"), new BlockItem(ModBlocks.WATER_FIR_TRAPDOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_planks_trapdoor"), new BlockItem(ModBlocks.WATER_FIR_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_boat"), new WaterFirBoat());
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_chest_boat"), new WaterFirChestBoat());

                // All water fir tree items
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_leaves"), new BlockItem(ModBlocks.WATER_FIR_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "autumnal_water_fir_leaves"), new BlockItem(ModBlocks.AUTUMNAL_WATER_FIR_LEAVES, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "water_fir_sapling"), new BlockItem(ModBlocks.WATER_FIR_SAPLING, new Item.Properties()));


                // All lantern items
                registrar.register(new ResourceLocation(MOD_ID, "zen_lantern"), new DoubleHighBlockItem(ModBlocks.ZEN_LANTERN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "soul_zen_lantern"), new DoubleHighBlockItem(ModBlocks.SOUL_ZEN_LANTERN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "paper_lantern"), new BlockItem(ModBlocks.PAPER_LANTERN, new Item.Properties()));

                // All architectural items
                registrar.register(new ResourceLocation(MOD_ID, "shoji_screen"), new DoubleHighBlockItem(ModBlocks.SHOJI_SCREEN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "dark_shoji_screen"), new DoubleHighBlockItem(ModBlocks.DARK_SHOJI_SCREEN, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "tatami_mat"), new BlockItem(ModBlocks.TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "long_tatami_mat"), new BlockItem(ModBlocks.LONG_TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "aged_tatami_mat"), new BlockItem(ModBlocks.AGED_TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "long_aged_tatami_mat"), new BlockItem(ModBlocks.LONG_AGED_TATAMI_MAT, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "terracotta_warrior_statue"), new DoubleHighBlockItem(ModBlocks.TERRACOTTA_WARRIOR_STATUE, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "red_fence"), new BlockItem(ModBlocks.RED_FENCE, new Item.Properties()));

                // Mushrooms
                registrar.register(new ResourceLocation(MOD_ID, "enoki_mushroom"), new BlockItem(ModBlocks.ENOKI_MUSHROOM, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "shiitake_mushroom"), new BlockItem(ModBlocks.SHIITAKE_MUSHROOM, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "enoki_mushroom_block"), new BlockItem(ModBlocks.ENOKI_MUSHROOM_BLOCK, new Item.Properties()));

                // TODO: Find a way to add these to the composter
                // All food items
                registrar.register(new ResourceLocation(MOD_ID, "koi"), new Koi());
                registrar.register(new ResourceLocation(MOD_ID, "cooked_koi"), new CookedKoi());
                registrar.register(new ResourceLocation(MOD_ID, "rice"), new Rice());
                registrar.register(new ResourceLocation(MOD_ID, "onigiri"), new Onigiri());
                registrar.register(new ResourceLocation(MOD_ID, "congee"), new Congee());

                // All bottled items
                registrar.register(new ResourceLocation(MOD_ID, "maple_syrup_bottle"), new MapleSyrupBottleItem());

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
                registrar.register(new ResourceLocation(MOD_ID, "bamboo_planks_trapdoor"), new BlockItem(ModBlocks.BAMBOO_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cherry_planks_trapdoor"), new BlockItem(ModBlocks.CHERRY_PLANKS_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "smooth_stone_trapdoor"), new BlockItem(ModBlocks.SMOOTH_STONE_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "stone_trapdoor"), new BlockItem(ModBlocks.STONE_TRAP_DOOR, new Item.Properties()));
                registrar.register(new ResourceLocation(MOD_ID, "cobblestone_trapdoor"), new BlockItem(ModBlocks.COBBLESTONE_TRAP_DOOR, new Item.Properties()));
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
            event.put(ModEntityTypeInitializer.KOI_ENTITY_TYPE.get(), AbstractSchoolingFish.createAttributes().build());
            event.put(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), Tanooki.createAttributes().build());
            event.put(ModEntityTypeInitializer.TERRACOTTA_WARRIOR_ENTITY_TYPE.get(), TerracottaWarrior.createAttributes().build());
        }

        @SubscribeEvent
        public static void onEntitySpawn(final SpawnPlacementRegisterEvent registerEvent) {
            registerEvent.register(ModEntityTypeInitializer.KOI_ENTITY_TYPE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            registerEvent.register(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tanooki::checkTanookiSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            registerEvent.register(ModEntityTypeInitializer.TERRACOTTA_WARRIOR_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TerracottaWarrior::checkTerracottaWarriorSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        }

        /**
         * Used to register features into the game using the mod event bus
         *
         * @param registerEvent The register event with which features will be registered
         */
        @SubscribeEvent
        public static void onFeaturesRegistry(final RegisterEvent registerEvent) {
            registerEvent.register(ForgeRegistries.Keys.FEATURES, registrar -> {
                // All ground cover features
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaf_ground_cover"), new MapleLeafPileCoverFeature());
                registrar.register(new ResourceLocation(MOD_ID, "moss_layer"), new MossLayerFeature());

                // Misc overworld features
                registrar.register(new ResourceLocation(MOD_ID, "hotspring"), new HotspringFeature(HotspringFeature.Configuration.CODEC));
                registrar.register(new ResourceLocation(MOD_ID, "karst_stone"), new KarstStoneFeature(KarstStoneFeature.Configuration.CODEC));
                registrar.register(new ResourceLocation(MOD_ID, "fallen_tree"), new FallenTreeFeature(FallenTreeFeature.Configuration.CODEC));
                registrar.register(new ResourceLocation(MOD_ID, "huge_enoki_mushroom"), new HugeEnokiMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
                registrar.register(new ResourceLocation(MOD_ID, "huge_shiitake_mushroom"), new HugeShiitakeMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
                registrar.register(new ResourceLocation(MOD_ID, "rice_paddy"), new RicePaddyFeature(RicePaddyFeature.Configuration.CODEC));
            });

            //TODO: Should I register configured features in here after the feature registration has run? Right now they register at the global level spread across a few new classes and sit in holders. This might not be the right stage to be registering them...
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
                    //.add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) ModConfiguredFeatures::bootstrap)
                    //.add(Registries.PLACED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) ModPlacements::bootstrap)
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
                // All black pine tree foliage placer types
                registrar.register(new ResourceLocation(MOD_ID, "black_pine_tree_foliage_placer"), new BlackPineFoliagePlacerType());
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
                registrar.register(new ResourceLocation(MOD_ID, "maple_leaf"), ModParticles.MAPLE_LEAF);
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
         * Used to register items into vanilla creative mode tabs in the creative mode UI using the mod event bus
         *
         * @param creativeTabBuilderRegistryEvent The registry event with which new items are added to vanilla creative mode tabs
         */
        @SubscribeEvent
        public static void onCreativeModeTabBuilderRegister(BuildCreativeModeTabContentsEvent creativeTabBuilderRegistryEvent) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = creativeTabBuilderRegistryEvent.getEntries();
            CreativeModeTab.TabVisibility visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

            if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                // Wood blocks
                entries.putAfter(new ItemStack(Items.MANGROVE_BUTTON), new ItemStack(ModItems.MAPLE_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_LOG), new ItemStack(ModItems.MAPLE_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_WOOD), new ItemStack(ModItems.STRIPPED_MAPLE_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_MAPLE_LOG), new ItemStack(ModItems.STRIPPED_MAPLE_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_MAPLE_WOOD), new ItemStack(ModItems.MAPLE_PLANKS), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_PLANKS), new ItemStack(ModItems.MAPLE_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_STAIRS), new ItemStack(ModItems.MAPLE_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_SLAB), new ItemStack(ModItems.MAPLE_FENCE), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_FENCE), new ItemStack(ModItems.MAPLE_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_FENCE_GATE), new ItemStack(ModItems.MAPLE_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_DOOR), new ItemStack(ModItems.MAPLE_TRAPDOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_TRAPDOOR), new ItemStack(ModItems.MAPLE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_PRESSURE_PLATE), new ItemStack(ModItems.MAPLE_BUTTON), visibility);

                entries.putAfter(new ItemStack(ModItems.MAPLE_BUTTON), new ItemStack(ModItems.BLACK_PINE_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_LOG), new ItemStack(ModItems.BLACK_PINE_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_WOOD), new ItemStack(ModItems.STRIPPED_BLACK_PINE_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_BLACK_PINE_LOG), new ItemStack(ModItems.STRIPPED_BLACK_PINE_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_BLACK_PINE_WOOD), new ItemStack(ModItems.BLACK_PINE_PLANKS), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_PLANKS), new ItemStack(ModItems.BLACK_PINE_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_STAIRS), new ItemStack(ModItems.BLACK_PINE_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_SLAB), new ItemStack(ModItems.BLACK_PINE_FENCE), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_FENCE), new ItemStack(ModItems.BLACK_PINE_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_FENCE_GATE), new ItemStack(ModItems.BLACK_PINE_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_DOOR), new ItemStack(ModItems.BLACK_PINE_TRAPDOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_TRAPDOOR), new ItemStack(ModItems.BLACK_PINE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_PRESSURE_PLATE), new ItemStack(ModItems.BLACK_PINE_BUTTON), visibility);

                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_BUTTON), new ItemStack(ModItems.HINOKI_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_LOG), new ItemStack(ModItems.HINOKI_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_WOOD), new ItemStack(ModItems.STRIPPED_HINOKI_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_HINOKI_LOG), new ItemStack(ModItems.STRIPPED_HINOKI_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_HINOKI_WOOD), new ItemStack(ModItems.HINOKI_PLANKS), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_PLANKS), new ItemStack(ModItems.HINOKI_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_STAIRS), new ItemStack(ModItems.HINOKI_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_SLAB), new ItemStack(ModItems.HINOKI_FENCE), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_FENCE), new ItemStack(ModItems.HINOKI_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_FENCE_GATE), new ItemStack(ModItems.HINOKI_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_DOOR), new ItemStack(ModItems.HINOKI_TRAPDOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_TRAPDOOR), new ItemStack(ModItems.HINOKI_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_PRESSURE_PLATE), new ItemStack(ModItems.HINOKI_BUTTON), visibility);

                entries.putAfter(new ItemStack(ModItems.HINOKI_BUTTON), new ItemStack(ModItems.WATER_FIR_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_LOG), new ItemStack(ModItems.WATER_FIR_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_WOOD), new ItemStack(ModItems.STRIPPED_WATER_FIR_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_WATER_FIR_LOG), new ItemStack(ModItems.STRIPPED_WATER_FIR_WOOD), visibility);
                entries.putAfter(new ItemStack(ModItems.STRIPPED_WATER_FIR_WOOD), new ItemStack(ModItems.WATER_FIR_PLANKS), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_PLANKS), new ItemStack(ModItems.WATER_FIR_STAIRS), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_STAIRS), new ItemStack(ModItems.WATER_FIR_SLAB), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_SLAB), new ItemStack(ModItems.WATER_FIR_FENCE), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_FENCE), new ItemStack(ModItems.WATER_FIR_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_FENCE_GATE), new ItemStack(ModItems.WATER_FIR_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_DOOR), new ItemStack(ModItems.WATER_FIR_TRAPDOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_TRAPDOOR), new ItemStack(ModItems.WATER_FIR_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_PRESSURE_PLATE), new ItemStack(ModItems.WATER_FIR_BUTTON), visibility);

                // Misc building blocks
                creativeTabBuilderRegistryEvent.accept(ModItems.SHOJI_SCREEN);
                creativeTabBuilderRegistryEvent.accept(ModItems.DARK_SHOJI_SCREEN);
                creativeTabBuilderRegistryEvent.accept(ModItems.TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.LONG_TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.AGED_TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.LONG_AGED_TATAMI_MAT);
                creativeTabBuilderRegistryEvent.accept(ModItems.TERRACOTTA_WARRIOR_STATUE);
                creativeTabBuilderRegistryEvent.accept(ModItems.RED_FENCE);

                // Hidden trapdoors
                entries.putAfter(new ItemStack(Items.ACACIA_TRAPDOOR), new ItemStack(ModItems.ACACIA_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.ACACIA_TRAPDOOR), new ItemStack(ModItems.BIRCH_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.DARK_OAK_TRAPDOOR), new ItemStack(ModItems.DARK_OAK_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.JUNGLE_TRAPDOOR), new ItemStack(ModItems.JUNGLE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.OAK_TRAPDOOR), new ItemStack(ModItems.OAK_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.SPRUCE_TRAPDOOR), new ItemStack(ModItems.SPRUCE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.MANGROVE_TRAPDOOR), new ItemStack(ModItems.MANGROVE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.BAMBOO_TRAPDOOR), new ItemStack(ModItems.BAMBOO_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_TRAPDOOR), new ItemStack(ModItems.CHERRY_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_TRAPDOOR), new ItemStack(ModItems.MAPLE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_PLANKS_TRAP_DOOR), new ItemStack(ModItems.BLACK_PINE_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_PLANKS_TRAP_DOOR), new ItemStack(ModItems.HINOKI_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_PLANKS_TRAP_DOOR), new ItemStack(ModItems.WATER_FIR_PLANKS_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.STONE_SLAB), new ItemStack(ModItems.STONE_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.SMOOTH_STONE_SLAB), new ItemStack(ModItems.SMOOTH_STONE_TRAP_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.COBBLESTONE_WALL), new ItemStack(ModItems.COBBLESTONE_TRAP_DOOR), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
                // Maple blocks
                entries.putAfter(new ItemStack(Items.CHERRY_LOG), new ItemStack(ModItems.MAPLE_LOG), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_LEAVES), new ItemStack(ModItems.MAPLE_LEAVES), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_SAPLING), new ItemStack(ModItems.MAPLE_SAPLING), visibility);
                entries.putAfter(new ItemStack(ModItems.RICE), new ItemStack(ModItems.MAPLE_LEAF), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_LEAF), new ItemStack(ModItems.MAPLE_LEAF_PILE), visibility);

                // Black pine blocks
                entries.putAfter(new ItemStack(ModItems.MAPLE_LOG), new ItemStack(ModItems.BLACK_PINE_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_LEAVES), new ItemStack(ModItems.BLACK_PINE_LEAVES), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_SAPLING), new ItemStack(ModItems.BLACK_PINE_SAPLING), visibility);

                // Hinoki blocks
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_LOG), new ItemStack(ModItems.HINOKI_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_LEAVES), new ItemStack(ModItems.HINOKI_LEAVES), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_SAPLING), new ItemStack(ModItems.HINOKI_SAPLING), visibility);

                // Water fir blocks
                entries.putAfter(new ItemStack(ModItems.HINOKI_LOG), new ItemStack(ModItems.WATER_FIR_LOG), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_LEAVES), new ItemStack(ModItems.WATER_FIR_LEAVES), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_LEAVES), new ItemStack(ModItems.AUTUMNAL_WATER_FIR_LEAVES), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_SAPLING), new ItemStack(ModItems.WATER_FIR_SAPLING), visibility);

                // TODO: Maybe this goes under food or something? Should check where it is in vanilla
                // Mushrooms
                entries.putAfter(new ItemStack(Items.BROWN_MUSHROOM), new ItemStack(ModItems.ENOKI_MUSHROOM), visibility);
                entries.putAfter(new ItemStack(ModItems.ENOKI_MUSHROOM), new ItemStack(ModItems.SHIITAKE_MUSHROOM), visibility);
                entries.putAfter(new ItemStack(Items.BROWN_MUSHROOM_BLOCK), new ItemStack(ModItems.ENOKI_MUSHROOM_BLOCK), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                // Lanterns
                entries.putAfter(new ItemStack(Items.SOUL_LANTERN), new ItemStack(ModItems.ZEN_LANTERN), visibility);
                entries.putAfter(new ItemStack(ModItems.ZEN_LANTERN), new ItemStack(ModItems.SOUL_ZEN_LANTERN), visibility);
                entries.putAfter(new ItemStack(ModItems.SOUL_ZEN_LANTERN), new ItemStack(ModItems.PAPER_LANTERN), visibility);

                // Signs
                entries.putAfter(new ItemStack(Items.CHERRY_SIGN), new ItemStack(ModItems.MAPLE_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_SIGN), new ItemStack(ModItems.MAPLE_HANGING_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_HANGING_SIGN), new ItemStack(ModItems.BLACK_PINE_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_SIGN), new ItemStack(ModItems.BLACK_PINE_HANGING_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_HANGING_SIGN), new ItemStack(ModItems.HINOKI_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_SIGN), new ItemStack(ModItems.HINOKI_HANGING_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_HANGING_SIGN), new ItemStack(ModItems.WATER_FIR_SIGN), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_SIGN), new ItemStack(ModItems.WATER_FIR_HANGING_SIGN), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
                // Maple blocks
                entries.putAfter(new ItemStack(Items.CHERRY_BUTTON), new ItemStack(ModItems.MAPLE_BUTTON), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_PRESSURE_PLATE), new ItemStack(ModItems.MAPLE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_CHEST_BOAT), new ItemStack(ModItems.MAPLE_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_FENCE_GATE), new ItemStack(ModItems.MAPLE_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_DOOR), new ItemStack(ModItems.MAPLE_DOOR), visibility);
                entries.putAfter(new ItemStack(Items.CHERRY_TRAPDOOR), new ItemStack(ModItems.MAPLE_TRAPDOOR), visibility);

                // Black Pine blocks
                entries.putAfter(new ItemStack(ModItems.MAPLE_BUTTON), new ItemStack(ModItems.BLACK_PINE_BUTTON), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_PRESSURE_PLATE), new ItemStack(ModItems.BLACK_PINE_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_CHEST_BOAT), new ItemStack(ModItems.BLACK_PINE_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_FENCE_GATE), new ItemStack(ModItems.BLACK_PINE_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_DOOR), new ItemStack(ModItems.BLACK_PINE_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_TRAPDOOR), new ItemStack(ModItems.BLACK_PINE_TRAPDOOR), visibility);

                // Hinoki blocks
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_BUTTON), new ItemStack(ModItems.HINOKI_BUTTON), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_PRESSURE_PLATE), new ItemStack(ModItems.HINOKI_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_CHEST_BOAT), new ItemStack(ModItems.HINOKI_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_FENCE_GATE), new ItemStack(ModItems.HINOKI_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_DOOR), new ItemStack(ModItems.HINOKI_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_TRAPDOOR), new ItemStack(ModItems.HINOKI_TRAPDOOR), visibility);

                // Water fir blocks
                entries.putAfter(new ItemStack(ModItems.HINOKI_BUTTON), new ItemStack(ModItems.WATER_FIR_BUTTON), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_PRESSURE_PLATE), new ItemStack(ModItems.WATER_FIR_PRESSURE_PLATE), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_CHEST_BOAT), new ItemStack(ModItems.WATER_FIR_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_FENCE_GATE), new ItemStack(ModItems.WATER_FIR_FENCE_GATE), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_DOOR), new ItemStack(ModItems.WATER_FIR_DOOR), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_TRAPDOOR), new ItemStack(ModItems.WATER_FIR_TRAPDOOR), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                // Buckets
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH_BUCKET), new ItemStack(ModItems.KOI_BUCKET), visibility);

                // Boats
                entries.putAfter(new ItemStack(Items.CHERRY_CHEST_BOAT), new ItemStack(ModItems.MAPLE_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_BOAT), new ItemStack(ModItems.MAPLE_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.MAPLE_CHEST_BOAT), new ItemStack(ModItems.BLACK_PINE_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_BOAT), new ItemStack(ModItems.BLACK_PINE_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.BLACK_PINE_CHEST_BOAT), new ItemStack(ModItems.HINOKI_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_BOAT), new ItemStack(ModItems.HINOKI_CHEST_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.HINOKI_CHEST_BOAT), new ItemStack(ModItems.WATER_FIR_BOAT), visibility);
                entries.putAfter(new ItemStack(ModItems.WATER_FIR_BOAT), new ItemStack(ModItems.WATER_FIR_CHEST_BOAT), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.COMBAT) {
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
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                // Crops
                entries.putAfter(new ItemStack(Items.BEETROOT), new ItemStack(ModItems.RICE), visibility);
                entries.putAfter(new ItemStack(ModItems.RICE), new ItemStack(ModItems.ONIGIRI), visibility);
                entries.putAfter(new ItemStack(ModItems.ONIGIRI), new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE), visibility);

                // Fish
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH), new ItemStack(ModItems.KOI), visibility);
                entries.putAfter(new ItemStack(ModItems.KOI), new ItemStack(ModItems.COOKED_KOI), visibility);

                // Meals
                entries.putAfter(new ItemStack(Items.PUMPKIN_PIE), new ItemStack(ModItems.CONGEE), visibility);
                entries.putAfter(new ItemStack(ModItems.CONGEE), new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                // Plants
                entries.putAfter(new ItemStack(Items.WHEAT), new ItemStack(ModItems.MAPLE_LEAF), visibility);
            } else if (creativeTabBuilderRegistryEvent.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                // Fish
                entries.putAfter(new ItemStack(Items.TROPICAL_FISH_SPAWN_EGG), new ItemStack(ModItems.KOI_SPAWN_EGG), visibility);

                // Animals
                // TODO: Put after whatever is the last animal spawn egg in the overworld
                entries.putAfter(new ItemStack(Items.COW_SPAWN_EGG), new ItemStack(ModItems.TANOOKI_SPAWN_EGG), visibility);

                // TODO: Put after whatever is the last monster spawn egg in the overworld
                // Monsters
                entries.putAfter(new ItemStack(Items.ZOMBIE_SPAWN_EGG), new ItemStack(ModItems.TERRACOTTA_WARRIOR_SPAWN_EGG), visibility);
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
            final Map<Block, Block> BLOCK_STRIPPING_MAP = (
                new ImmutableMap.Builder<Block, Block>()
                    .put(ModBlocks.MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_LOG)
                    .put(ModBlocks.MAPLE_WOOD, ModBlocks.STRIPPED_MAPLE_WOOD)
                    .put(ModBlocks.BLACK_PINE_LOG, ModBlocks.STRIPPED_BLACK_PINE_LOG)
                    .put(ModBlocks.BLACK_PINE_WOOD, ModBlocks.STRIPPED_BLACK_PINE_WOOD)
                    .put(ModBlocks.HINOKI_LOG, ModBlocks.STRIPPED_HINOKI_LOG)
                    .put(ModBlocks.HINOKI_WOOD, ModBlocks.STRIPPED_HINOKI_WOOD)
                    .put(ModBlocks.WATER_FIR_LOG, ModBlocks.STRIPPED_WATER_FIR_LOG)
                    .put(ModBlocks.WATER_FIR_WOOD, ModBlocks.STRIPPED_WATER_FIR_WOOD)
            ).build();

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
                if (player.jumping && player.noJumpDelay <= 0) {

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
