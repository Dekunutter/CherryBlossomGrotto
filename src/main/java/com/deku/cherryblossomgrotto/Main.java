package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.client.network.handlers.DoubleJumpClientMessageHandler;
import com.deku.cherryblossomgrotto.client.network.messages.DoubleJumpClientMessage;
import com.deku.cherryblossomgrotto.client.renderers.KoiRenderer;
import com.deku.cherryblossomgrotto.client.renderers.KunaiRenderer;
import com.deku.cherryblossomgrotto.client.renderers.ModBoatRenderer;
import com.deku.cherryblossomgrotto.client.renderers.ShurikenRenderer;
import com.deku.cherryblossomgrotto.common.blocks.*;
import com.deku.cherryblossomgrotto.common.capabilities.DoubleJumpCapability;
import com.deku.cherryblossomgrotto.common.capabilities.ModCapabilitiesInitializer;
import com.deku.cherryblossomgrotto.common.enchantments.ModEnchantmentInitializer;
import com.deku.cherryblossomgrotto.common.entity.EntityTypeInitializer;
import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.features.*;
import com.deku.cherryblossomgrotto.common.features.template.ModProcessorLists;
import com.deku.cherryblossomgrotto.common.items.*;
import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalFactory;
import com.deku.cherryblossomgrotto.common.particles.ModParticles;
import com.deku.cherryblossomgrotto.common.recipes.FoldingRecipe;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryBlossomSignTileEntity;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryLeavesTileEntity;
import com.deku.cherryblossomgrotto.common.tileEntities.ModTileEntityData;
import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider.CherryBlossomForestFlowerProviderType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.structures.*;
import com.deku.cherryblossomgrotto.common.world.gen.topLayerModifications.ModTopLayerModifications;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.*;
import com.deku.cherryblossomgrotto.server.network.handlers.DoubleJumpServerMessageHandler;
import com.deku.cherryblossomgrotto.server.network.messages.DoubleJumpServerMessage;
import com.deku.cherryblossomgrotto.utils.LogTweaker;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.deku.cherryblossomgrotto.common.capabilities.ModCapabilitiesInitializer.DOUBLE_JUMP_CAPABILITY;
import static com.deku.cherryblossomgrotto.common.enchantments.ModEnchantmentInitializer.DOUBLE_JUMP_ENCHANTMENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_SERVER;

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
        LOGGER.info("HELLO FROM MAIN");

        NETWORK_CHANNEL =  NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "cherryblossomgrottochannel"), () -> NETWORK_PROTOCOL_VERSION, DoubleJumpClientMessageHandler::isProtocolAcceptedOnClient, DoubleJumpServerMessageHandler::isProtocolAcceptedOnServer);

        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Biome logic
        ModBiomeInitializer.BIOMES.register(eventBus);
        ModBiomeInitializer.registerBiomes();

        // Structure logic
        ModStructurePieceTypes.register();
        ModStructureInitializer.STRUCTURES.register(eventBus);

        // Enchantment logic
        ModEnchantmentInitializer.ENCHANTMENTS.register(eventBus);

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        ClientOnlyRegistrar clientOnlyRegistrar = new ClientOnlyRegistrar(eventBus);

        //top layer generation logic
        ModTopLayerModifications.TOP_LAYER_MODIFICATIONS.register(eventBus);

        // Register ourselves for server and other game events we are interested in
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(EventPriority.HIGH, this::biomeModification);
        forgeEventBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeEventBus.register(this);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientOnlyRegistrar::registerClientOnlyEvents);
    }

    /**
     * Sets up logic that is common to both the client and server
     *
     * In this case we are registering our custom network messages to the simple network channel.
     * Then we register our custom wood types so that we can use associated resources.
     * Then we initialize all our modded structures.
     * Finally, we initialize all our modded capabilities.
     *
     * @param event The setup event
     */
    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT COMMON");

        NETWORK_CHANNEL.registerMessage(DoubleJumpServerMessage.MESSAGE_ID, DoubleJumpServerMessage.class, DoubleJumpServerMessage::encode, DoubleJumpServerMessage::decode, DoubleJumpServerMessageHandler::onMessageReceived, Optional.of(PLAY_TO_SERVER));
        NETWORK_CHANNEL.registerMessage(DoubleJumpClientMessage.MESSAGE_ID, DoubleJumpClientMessage.class, DoubleJumpClientMessage::encode, DoubleJumpClientMessage::decode, DoubleJumpClientMessageHandler::onMessageReceived, Optional.of(PLAY_TO_CLIENT));

        event.enqueueWork(() -> {
            WoodType.register(ModWoodType.CHERRY_BLOSSOM);
            ModStructureInitializer.setupStructures();
            ModProcessorLists.register();
            ModConfiguredStructureInitializer.registerConfiguredStructures();
            ModCapabilitiesInitializer.registerCapabilities();
        });
    }

    /**
     * Sets up client specific logic, such as rendering types.
     *
     * In this case we are performing the following:
     * - adding the cut-out render type to our custom grass block so that overlays display properly on that block.
     * - Rendering some textures as cutout mipmaps so that opacity is adhered to
     * - Binding the sign renderer to our custom sign tile entity.
     * - Adding our custom wood types to the atlas, which enables them to be valid sign materials.
     *
     * @param event The client setup event
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityData.MOD_BOAT_DATA, ModBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityData.KUNAI_DATA, KunaiRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityData.SHURIKEN_DATA, ShurikenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityData.KOI_DATA, KoiRenderer::new);

        //RenderTypeLookup.setRenderLayer(ModBlocks.GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_PETALS, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_SAPLING, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_CHERRY_SAPLING, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.RICE_PADDY, RenderType.cutoutMipped());

        ClientRegistry.bindTileEntityRenderer(ModTileEntityData.CHERRY_SIGN_TILE_DATA, SignTileEntityRenderer::new);

        event.enqueueWork(() -> {
            Atlases.addWoodType(ModWoodType.CHERRY_BLOSSOM);
        });
    }

    /**
     * Modifies biomes as they are loading into the world via a forge event.
     * We ensure that modded structures can be loaded into specific vanilla biomes here
     *
     * @param event The event thrown by the biome being loaded.
     */
    private void biomeModification(final BiomeLoadingEvent event) {
        if (event.getName().equals(Biomes.BAMBOO_JUNGLE.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA).addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_TORII_GATE);
        }
        if (event.getName().equals(Biomes.BAMBOO_JUNGLE_HILLS.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA).addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_TORII_GATE);
        }
        if (event.getName().equals(Biomes.JUNGLE.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.JUNGLE_EDGE.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.JUNGLE_HILLS.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.MODIFIED_JUNGLE_EDGE.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.MODIFIED_JUNGLE.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.SNOWY_MOUNTAINS.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA).addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_TORII_GATE);
        }
        if (event.getName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.GIANT_SPRUCE_TAIGA_HILLS.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA);
        }
        if (event.getName().equals(Biomes.BEACH.location())) {
            event.getGeneration().addStructureStart(ModConfiguredStructureInitializer.CONFIGURED_TORII_GATE);
        }
    }

    /**
     * Adds dimensional spacing to all structures as they load into the world via a forge event.
     * This function basically has the final say on if a structure can be spawned in a given dimension or generator.
     * It blocks modded structures from spawning in flat worlds since that is preferred by those map types.
     * It also adds modded structures to the vanilla structure config which allows the game to actually spawn the structures
     * according to their separation settings.
     *
     * @param event The event thrown by the world being loaded
     */
    private void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld =  (ServerWorld) event.getWorld();
            if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }

            serverWorld.getChunkSource().generator.getSettings().structureConfig().putIfAbsent(ModStructureInitializer.GIANT_BUDDHA.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructureInitializer.GIANT_BUDDHA.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig().putIfAbsent(ModStructureInitializer.TORII_GATE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructureInitializer.TORII_GATE.get()));
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("cherryblossomgrotto", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    /**
     * Inner class for different event registers used by the mod
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /**
         * Used to register blocks into the game using the mod event bus
         *
         * @param blockRegistryEvent The registry event with which blocks will be registered
         */
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            LOGGER.info("HELLO from Register Block");

            blockRegistryEvent.getRegistry().register(new CherryBlossomLog());
            blockRegistryEvent.getRegistry().register(new StrippedCherryBlossomLog());
            blockRegistryEvent.getRegistry().register(new CherryBlossomWood());
            blockRegistryEvent.getRegistry().register(new StrippedCherryBlossomWood());
            blockRegistryEvent.getRegistry().register(new CherryBlossomPlanks());
            blockRegistryEvent.getRegistry().register(new CherryBlossomSlab());
            blockRegistryEvent.getRegistry().register(new CherryBlossomStairs());
            blockRegistryEvent.getRegistry().register(new CherryBlossomButton());
            blockRegistryEvent.getRegistry().register(new CherryBlossomFence());
            blockRegistryEvent.getRegistry().register(new CherryBlossomFenceGate());
            blockRegistryEvent.getRegistry().register(new CherryBlossomPressurePlate());
            blockRegistryEvent.getRegistry().register(new CherryBlossomSign());
            blockRegistryEvent.getRegistry().register(new CherryBlossomWallSign());
            blockRegistryEvent.getRegistry().register(new CherryBlossomDoor());
            blockRegistryEvent.getRegistry().register(new CherryBlossomTrapDoor());

            blockRegistryEvent.getRegistry().register(new CherryBlossomLeaves());

            blockRegistryEvent.getRegistry().register(new CherryBlossomPetals());

            blockRegistryEvent.getRegistry().register(new CherryBlossomSapling());
            blockRegistryEvent.getRegistry().register(new PottedCherryBlossomSapling());

            blockRegistryEvent.getRegistry().register(new ShojiScreen());

            blockRegistryEvent.getRegistry().register(new RicePaddy());

            blockRegistryEvent.getRegistry().register(new ZenLantern());
            blockRegistryEvent.getRegistry().register(new SoulZenLantern());

            blockRegistryEvent.getRegistry().register(new StoneTrapdoor());
            blockRegistryEvent.getRegistry().register(new SmoothStoneTrapdoor());
            blockRegistryEvent.getRegistry().register(new CobblestoneTrapdoor());
            blockRegistryEvent.getRegistry().register(new OakPlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new DarkOakPlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new AcaciaPlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new JunglePlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new SprucePlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new BirchPlanksTrapdoor());
            blockRegistryEvent.getRegistry().register(new CherryBlossomPlanksTrapdoor());
        }

        /**
         * Used to register tile entities into the game using the mod event bus
         * Associated entity tile data is assigned before registration
         *
         * @param tileEntityRegistryEvent The registry event with which tile entities will be registered
         */
        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent) {
            TileEntityType<CherryLeavesTileEntity> cherryLeavesDataType = TileEntityType.Builder.of(CherryLeavesTileEntity::new, ModBlocks.CHERRY_LEAVES.getBlock()).build(null);
            cherryLeavesDataType.setRegistryName("cherryblossomgrotto:cherry_leaves_tile_entity");
            tileEntityRegistryEvent.getRegistry().register(cherryLeavesDataType);

            TileEntityType<CherryBlossomSignTileEntity> cherrySignDataType = TileEntityType.Builder.of(CherryBlossomSignTileEntity::new, ModBlocks.CHERRY_SIGN.getBlock(), ModBlocks.CHERRY_WALL_SIGN.getBlock()).build(null);
            cherrySignDataType.setRegistryName("cherryblossomgrotto:cherry_sign_tile_entity");
            tileEntityRegistryEvent.getRegistry().register(cherrySignDataType);
        }

        /**
         * Used to register items into the game using the mod event bus
         *
         * @param itemRegistryEvent The registry event with which items will be registered
         */
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            LOGGER.info("HELLO from Register Item");

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_LOG, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("cherry_blossom_log"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.STRIPPED_CHERRY_LOG, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("stripped_cherry_blossom_log"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_WOOD, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("cherry_blossom_wood"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.STRIPPED_CHERRY_WOOD, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("stripped_cherry_blossom_wood"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_PLANKS, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("cherry_blossom_planks"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_SLAB, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("cherry_blossom_slab"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_STAIRS, new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)).setRegistryName("cherry_blossom_stairs"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_BUTTON, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_button"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_FENCE, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("cherry_blossom_fence"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_FENCE_GATE, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_fence_gate"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_PRESSURE_PLATE, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_pressure_plate"));
            itemRegistryEvent.getRegistry().register(new SignItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS), ModBlocks.CHERRY_SIGN, ModBlocks.CHERRY_WALL_SIGN).setRegistryName("cherry_blossom_sign"));
            itemRegistryEvent.getRegistry().register(new TallBlockItem(ModBlocks.CHERRY_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_door"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_trapdoor"));
            itemRegistryEvent.getRegistry().register(new CherryBlossomBoat());

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_LEAVES, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("cherry_blossom_leaves"));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_PETALS, new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName("cherry_blossom_petals"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_SAPLING, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("cherry_blossom_sapling"));

            itemRegistryEvent.getRegistry().register(new TallBlockItem(ModBlocks.ZEN_LANTERN, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("zen_lantern"));
            itemRegistryEvent.getRegistry().register(new TallBlockItem(ModBlocks.SOUL_ZEN_LANTERN, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("soul_zen_lantern"));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.SHOJI_SCREEN, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("shoji_screen"));

            itemRegistryEvent.getRegistry().register(new KoiBucket(EntityTypeInitializer.KOI_ENTITY_TYPE));

            // TODO: Find a way to add these to the composter
            itemRegistryEvent.getRegistry().register(new Koi());
            itemRegistryEvent.getRegistry().register(new CookedKoi());
            itemRegistryEvent.getRegistry().register(new Rice());
            itemRegistryEvent.getRegistry().register(new Onigiri());

            itemRegistryEvent.getRegistry().register(new CherryBlossomPetal());

            itemRegistryEvent.getRegistry().register(new Katana());
            itemRegistryEvent.getRegistry().register(new Kunai());
            itemRegistryEvent.getRegistry().register(new Shuriken());

            itemRegistryEvent.getRegistry().register(new NinjaMask());
            itemRegistryEvent.getRegistry().register(new NinjaTunic());
            itemRegistryEvent.getRegistry().register(new NinjaLeggings());
            itemRegistryEvent.getRegistry().register(new NinjaSandals());
            itemRegistryEvent.getRegistry().register(new KabutoHelmet());
            itemRegistryEvent.getRegistry().register(new KabutoCuirass());
            itemRegistryEvent.getRegistry().register(new KabutoGreaves());
            itemRegistryEvent.getRegistry().register(new KabutoSandals());

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ACACIA_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("acacia_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.BIRCH_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("birch_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.DARK_OAK_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("dark_oak_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.JUNGLE_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("jungle_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.OAK_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("oak_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.SPRUCE_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("spruce_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_BLOSSOM_PLANKS_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cherry_blossom_planks_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_STONE_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("smooth_stone_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.STONE_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("stone_trapdoor"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.COBBLESTONE_TRAP_DOOR, new Item.Properties().tab(ItemGroup.TAB_REDSTONE)).setRegistryName("cobblestone_trapdoor"));
        }

        /**
         * Used to attach attribute modifiers to entities using the Forge event bus.
         * Required for living entities.
         *
         * @param event The attachment event with which attribute modifiers will be attached to different entity types
         */
        @SubscribeEvent
        public static void onEntityAttributeRegistration(final EntityAttributeCreationEvent event) {
            LOGGER.info("HELLO from Register Entity Attribute");

            event.put(EntityTypeInitializer.KOI_ENTITY_TYPE, AbstractFishEntity.createAttributes().build());

            GlobalEntityTypeAttributes.put(EntityTypeInitializer.KOI_ENTITY_TYPE, AbstractFishEntity.createAttributes().build());
        }

        /**
         * Used to register entities into the game using the mod event bus
         * Associated entity data is assigned before registration
         *
         * @param entityRegistryEvent The registry event with which entities will be registered
         */
        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
            LOGGER.info("HELLO from Register Entity");

            EntityTypeInitializer.BOAT_ENTITY_TYPE.setRegistryName("mod_boat_entity");
            entityRegistryEvent.getRegistry().register(EntityTypeInitializer.BOAT_ENTITY_TYPE);

            EntityTypeInitializer.KUNAI_ENTITY_TYPE.setRegistryName("kunai_entity");
            entityRegistryEvent.getRegistry().register(EntityTypeInitializer.KUNAI_ENTITY_TYPE);

            EntityTypeInitializer.SHURIKEN_ENTITY_TYPE.setRegistryName("shuriken_entity");
            entityRegistryEvent.getRegistry().register(EntityTypeInitializer.SHURIKEN_ENTITY_TYPE);

            EntityTypeInitializer.KOI_ENTITY_TYPE.setRegistryName("koi_entity");
            entityRegistryEvent.getRegistry().register(EntityTypeInitializer.KOI_ENTITY_TYPE);
        }

        /**
         * Used to register features into the game using the mod event bus
         *
         * @param featureRegistryEvent The registry event with which features will be registered
         */
        @SubscribeEvent
        public static void onFeaturesRegistry(final RegistryEvent.Register<Feature<?>> featureRegistryEvent) {
            LOGGER.info("HELLO from Register Feature");

            ModConfiguredFeatures.CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree", new CherryBlossomTreeConfiguredFeature());
            ModConfiguredFeatures.FANCY_CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":fancy_cherry_blossom_tree", new FancyCherryBlossomTreeConfiguredFeature());
            ModConfiguredFeatures.GRAND_CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":grand_cherry_blossom_tree", new GrandCherryBlossomTreeConfiguredFeature());
            ModConfiguredFeatures.CHERRY_TREE_BEES_0002 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_0002", new CherryBlossomTreeBees0002ConfiguredFeature());
            ModConfiguredFeatures.CHERRY_TREE_BEES_002 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_002", new CherryBlossomTreeBees002ConfiguredFeature());
            ModConfiguredFeatures.CHERRY_TREE_BEES_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_005", new CherryBlossomTreeBees005ConfiguredFeature());
            ModConfiguredFeatures.FANCY_CHERRY_TREE_BEES_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":fancy_cherry_blossom_tree_bees_005", new FancyCherryBlossomTreeBees005ConfiguredFeature());

            ModConfiguredFeatures.CHERRY_PETAL_COVER = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_petal_ground_cover", new CherryBlossomPetalCoverConfiguredFeature());

            ModConfiguredFeatures.IRON_ORE_SPARSE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":ore_iron_sparse", new OreIronSparseConfiguredFeature());

            ModConfiguredFeatures.CHERRY_TREE_FOREST = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_forest", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(ModConfiguredFeatures.GRAND_CHERRY_TREE.weighted(0.01f), ModConfiguredFeatures.FANCY_CHERRY_TREE.weighted(0.1f)), ModConfiguredFeatures.CHERRY_TREE_BEES_002)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.1f, 1))));
            // TODO: Add this forest flowers provider back in (it doesnt work with double block flowers) to spawn different groups later. Its useful code, just got replaced by the simpler list logic below
            //ModFeatures.CHERRY_TREE_FOREST_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_forest_flowers", Feature.FLOWER.configured((new BlockClusterFeatureConfig.Builder(CherryBlossomGrottoFlowerBlockStateProvider.INSTANCE, new DoublePlantBlockPlacer())).tries(64).build()).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(100));

            ImmutableList<Supplier<ConfiguredFeature<?, ?>>> CHERRY_BLOSSOM_FOREST_FLOWER_FEATURES = ImmutableList.of(() -> {
                return Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILAC.defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build());
            }, () -> {
                return Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.LILY_OF_THE_VALLEY.defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).noProjection().build());
            }, () -> {
                return Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.PEONY.defaultBlockState()), new DoublePlantBlockPlacer())).tries(64).noProjection().build());
            }, () -> {
                return Feature.NO_BONEMEAL_FLOWER.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.ALLIUM.defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).build());
            });
            ModConfiguredFeatures.CHERRY_TREE_FOREST_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_forest_flowers", Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(CHERRY_BLOSSOM_FOREST_FLOWER_FEATURES)).count(FeatureSpread.of(-3, 4)).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(15));
        }

        @SubscribeEvent
        public static void onBlockStateProviderTypeRegistry(final RegistryEvent.Register<BlockStateProviderType<?>> blockStateProviderRegistryEvent) {
            LOGGER.info("HELLO from Register Block State Provider Type");

            blockStateProviderRegistryEvent.getRegistry().register(new CherryBlossomForestFlowerProviderType());
        }

        /**
         * Used to register recipe serializers into the game using the mod event bus
         *
         * @param recipeSerializerRegistryEvent The registry event with which recipe serializers will be registered
         */
        @SubscribeEvent
        public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> recipeSerializerRegistryEvent) {
            LOGGER.info("HELLO from Register Recipe Serializer");

            SpecialRecipeSerializer<FoldingRecipe> foldingSerializer = new SpecialRecipeSerializer<>(FoldingRecipe::new);
            foldingSerializer.setRegistryName(new ResourceLocation(MOD_ID,"folding"));
            recipeSerializerRegistryEvent.getRegistry().register(foldingSerializer);
        }

        /**
         * Used to register foliage placers into the game using the mod event bus
         *
         * @param foliagePlacerRegistryEvent The registry event with which foliage placers will be registered
         */
        @SubscribeEvent
        public static void onFoliagePlacerRegistry(final RegistryEvent.Register<FoliagePlacerType<?>> foliagePlacerRegistryEvent) {
            LOGGER.info("HELLO from Register Foliage Placer");

            foliagePlacerRegistryEvent.getRegistry().register(new CherryBlossomFoliagePlacerType());
            foliagePlacerRegistryEvent.getRegistry().register(new GrandCherryBlossomFoliagePlacerType());
        }

        /**
         * Used to register particle types into the game using the mod event bus
         *
         * @param particleTypeRegistryEvent The registry event with which particle types will be registered
         */
        @SubscribeEvent
        public static void onParticleTypeRegistry(final RegistryEvent.Register<ParticleType<?>> particleTypeRegistryEvent) {
            LOGGER.info("HELLO from Register Particle Type");

            particleTypeRegistryEvent.getRegistry().register(ModParticles.CHERRY_PETAL.setRegistryName("cherry_blossom_petal"));
        }

        /**
         * Used to register particle factories into the game using the mod event bus
         *
         * @param particleFactoryRegistryEvent The registry event with which particle factories will be registered
         */
        @SubscribeEvent
        public static void onParticleFactoryRegistry(final ParticleFactoryRegisterEvent particleFactoryRegistryEvent) {
            Main.LOGGER.info("HELLO from Register Particle Factory");

            Minecraft.getInstance().particleEngine.register(ModParticles.CHERRY_PETAL, FallingCherryBlossomPetalFactory::new);
        }

        /**
         * Used to register block color handlers into the game using the mod event bus
         *
         * @param event The registry event with which block color handlers will be registered
         */
        @SubscribeEvent
        public static void onBlockColorHandlerRegistration(ColorHandlerEvent.Block event) {
            LOGGER.info("HELLO from Register Block Color Handler");

            BlockColors blockColors = event.getBlockColors();
            //blockColors.register(GrassBlockColor.instance, ModBlocks.GRASS);
        }
    }

    /**
     * Inner class for different event registers used by the mod for world generation
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class TrunkPlacerRegistryEventHandler {
        public static TrunkPlacerType<CherryBlossomTrunkPlacer> CHERRY_TREE_TRUNK_PLACER;
        public static TrunkPlacerType<FancyCherryBlossomTrunkPlacer> FANCY_CHERRY_TREE_TRUNK_PLACER;
        public static TrunkPlacerType<GrandCherryBlossomTrunkPlacer> GRAND_CHERRY_TREE_TRUNK_PLACER;

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) throws InvocationTargetException, InstantiationException, IllegalAccessException {
            TrunkPlacerType<CherryBlossomTrunkPlacer> cherryBlossomTrunkPlacerType = CherryBlossomTrunkPlacerType.createTrunkPlacerType(CherryBlossomTrunkPlacer.CODEC);
            CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":cherry_blossom_tree_trunk_placer", cherryBlossomTrunkPlacerType);

            TrunkPlacerType<FancyCherryBlossomTrunkPlacer> fancyCherryBlossomTrunkPlacerType = FancyCherryBlossomTrunkPlacerType.createTrunkPlacerType(FancyCherryBlossomTrunkPlacer.CODEC);
            FANCY_CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":fancy_cherry_blossom_tree_trunk_placer", fancyCherryBlossomTrunkPlacerType);

            TrunkPlacerType<GrandCherryBlossomTrunkPlacer> grandCherryBlossomTrunkPlacerType = GrandCherryBlossomTrunkPlacerType.createTrunkPlacerType(GrandCherryBlossomTrunkPlacer.CODEC);
            GRAND_CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":big_cherry_blossom_tree_trunk_placer", grandCherryBlossomTrunkPlacerType);
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
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(new ResourceLocation(MOD_ID,"double_jump"), new DoubleJumpCapability());
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
                World world = event.getWorld();
                BlockPos position = event.getPos();
                BlockState state = world.getBlockState(position);
                Block block = BLOCK_STRIPPING_MAP.get(state.getBlock());
                if (block != null) {
                    LOGGER.info("STRIP EVENT occurred due to right-click");
                    PlayerEntity player = event.getPlayer();
                    world.playSound(player, position, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.setBlock(position, block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
                    event.getItemStack().hurtAndBreak(1, player, (p_220036_0_) -> p_220036_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                }
            }
        }

        @SubscribeEvent
        public static void onRenderPlayerEvent(RenderPlayerEvent.Pre event) {
            // TODO: Try manipulating the player arm in here (or in the POST event) to perform an action
            event.getRenderer().getModel().rightArm.visible = true;
        }

        @SubscribeEvent
        public static void onRenderPlayerEvent(RenderPlayerEvent.Post event) {
            //event.getRenderer().getModel().rightArm.translateAndRotate();
            event.getRenderer().getModel().rightArm.visible = true;
        }

        @SubscribeEvent
        public static void onRenderPlayerEvent(RenderHandEvent event) {
            if (event.getItemStack().getItem() == ModItems.SHURIKEN) {
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
                PlayerEntity player = event.player;
                boolean isJumping = (boolean) ForgeReflection.getObfuscatedPrivatizedFieldValue(LivingEntity.class, "field_70703_bu", player);
                int jumpCooldownTimer = (int) ForgeReflection.getObfuscatedPrivatizedFieldValue(LivingEntity.class, "field_70773_bE", player);
                if (isJumping && jumpCooldownTimer <= 0) {

                    if (!player.isPassenger() && !player.isFallFlying() && !player.isInWater() && !player.isInLava() && !player.isSleeping() && !player.isSwimming() && !player.isDeadOrDying()) {
                        if (player.getFoodData().getFoodLevel() > 3 && EnchantmentHelper.getEnchantmentLevel(DOUBLE_JUMP_ENCHANTMENT.get(), player) > 0) {
                            DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DOUBLE_JUMP_CAPABILITY).orElse(null);
                            if (doubleJumpCapability != null) {
                                if (!doubleJumpCapability.hasDoubleJumped()) {
                                    player.jumpFromGround();
                                    player.fallDistance = 0.0F;
                                    player.causeFoodExhaustion(player.isSprinting() ? 0.2F * 3.0F : 0.05F * 3.0F);
                                    ForgeReflection.setObfuscatedPrivatizedFieldToValue(LivingEntity.class, "field_70773_bE", 10, player);

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
            if (event.getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DOUBLE_JUMP_CAPABILITY).orElse(null);
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
        public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
            if (event.getEntity() instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
                boolean hasDoubleJumped = player.getCapability(DOUBLE_JUMP_CAPABILITY).map(DoubleJumpCapability.IDoubleJump::hasDoubleJumped).orElse(false);
                if (hasDoubleJumped) {
                    DoubleJumpClientMessage message = new DoubleJumpClientMessage(player.getUUID(), true);
                    Main.NETWORK_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
                }
            }
        }
    }
}
