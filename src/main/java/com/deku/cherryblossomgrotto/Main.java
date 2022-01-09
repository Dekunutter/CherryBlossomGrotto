package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.client.KunaiRenderer;
import com.deku.cherryblossomgrotto.client.ModBoatRenderer;
import com.deku.cherryblossomgrotto.client.ShurikenRenderer;
import com.deku.cherryblossomgrotto.common.blocks.*;
import com.deku.cherryblossomgrotto.common.entity.item.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import com.deku.cherryblossomgrotto.common.features.*;
import com.deku.cherryblossomgrotto.common.items.*;
import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalFactory;
import com.deku.cherryblossomgrotto.common.particles.ModParticles;
import com.deku.cherryblossomgrotto.common.recipes.FoldingRecipe;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryBlossomSignTileEntity;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryLeavesTileEntity;
import com.deku.cherryblossomgrotto.common.tileEntities.ModTileEntityData;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider.CherryBlossomForestFlowerProviderType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.BigCherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.structures.*;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.BigCherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.BigCherryBlossomTrunkPlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegistryEvent;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Structure logic
        ModStructurePieceTypes.register();
        ModStructureInitializer.STRUCTURES.register(eventBus);

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        ClientOnlyRegistrar clientOnlyRegistrar = new ClientOnlyRegistrar(eventBus);

        // Biome logic
        ModBiomeInitializer.BIOMES.register(eventBus);
        ModBiomeInitializer.registerBiomes();

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
     * In this case we are registering our custom wood types so that we can use associated resources.
     * Then we initialize all our modded structures.
     *
     * @param event The setup event
     */
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");

        event.enqueueWork(() -> {
            WoodType.register(ModWoodType.CHERRY_BLOSSOM);
            ModStructureInitializer.setupStructures();
            ModConfiguredStructureInitializer.registerConfiguredStructures();
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

        //RenderTypeLookup.setRenderLayer(ModBlocks.GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_PETALS, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_SAPLING, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_CHERRY_SAPLING, RenderType.cutoutMipped());

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

            // TODO: Commented out overridden grass block for possible removal in the future. Find all instances and remove if block goes unused
            //blockRegistryEvent.getRegistry().register(new NewGrassBlock());
            blockRegistryEvent.getRegistry().register(new CherryBlossomPetals());

            blockRegistryEvent.getRegistry().register(new CherryBlossomSapling());
            blockRegistryEvent.getRegistry().register(new PottedCherryBlossomSapling());

            //NOTE: Just a test on overriding base game blocks directly in the registry
            //blockRegistryEvent.getRegistry().register(new DirtTest());

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
            //itemRegistryEvent.getRegistry().register(new BlockItem(OverriddenBlocks.DIRT, new Item.Properties()).setRegistryName("dirt"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_SAPLING, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("cherry_blossom_sapling"));

            itemRegistryEvent.getRegistry().register(new TallBlockItem(ModBlocks.ZEN_LANTERN, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("zen_lantern"));
            itemRegistryEvent.getRegistry().register(new TallBlockItem(ModBlocks.SOUL_ZEN_LANTERN, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName("soul_zen_lantern"));

            itemRegistryEvent.getRegistry().register(new CherryBlossomPetal());

            itemRegistryEvent.getRegistry().register(new Katana());
            itemRegistryEvent.getRegistry().register(new Kunai());
            itemRegistryEvent.getRegistry().register(new Shuriken());

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
         * Used to register entities into the game using the mod event bus
         * Associated entity data is assigned before registration
         *
         * @param entityRegistryEvent The registry event with which entities will be registered
         */
        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
            LOGGER.info("HELLO from Register Entity");

            EntityType<Entity> modBoatEntity = EntityType.Builder.of(ModBoatEntity::new, EntityClassification.MISC).setCustomClientFactory(ModBoatEntity::new).sized(1.375f, 0.5625f).clientTrackingRange(10).build("mod_boat_entity");
            modBoatEntity.setRegistryName("mod_boat_entity");
            entityRegistryEvent.getRegistry().register(modBoatEntity);

            EntityType<Entity> kunaiEntity = EntityType.Builder.of(KunaiEntity::new, EntityClassification.MISC).setCustomClientFactory(KunaiEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("kunai_entity");
            kunaiEntity.setRegistryName("kunai_entity");
            entityRegistryEvent.getRegistry().register(kunaiEntity);

            EntityType<Entity> shurikenEntity = EntityType.Builder.of(ShurikenEntity::new, EntityClassification.MISC).setCustomClientFactory(ShurikenEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("shuriken_entity");
            shurikenEntity.setRegistryName("shuriken_entity");
            entityRegistryEvent.getRegistry().register(shurikenEntity);
        }

        /**
         * Used to register features into the game using the mod event bus
         *
         * @param featureRegistryEvent The registry event with which features will be registered
         */
        @SubscribeEvent
        public static void onFeaturesRegistry(final RegistryEvent.Register<Feature<?>> featureRegistryEvent) {
            LOGGER.info("HELLO from Register Feature");

            //featureRegistryEvent.getRegistry().register(new CherryBlossomTreeFeature(false));
            //featureRegistryEvent.getRegistry().register(new LargeCherryBlossomTreeFeature(false));

            ModFeatures.CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree", new CherryBlossomTreeConfiguredFeature());
            ModFeatures.BIG_CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":big_cherry_blossom_tree", new BigCherryBlossomTreeConfiguredFeature());
            ModFeatures.CHERRY_TREE_BEES_0002 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_0002", new CherryBlossomTreeBees0002ConfiguredFeature());
            ModFeatures.CHERRY_TREE_BEES_002 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_002", new CherryBlossomTreeBees002ConfiguredFeature());
            ModFeatures.CHERRY_TREE_BEES_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_bees_005", new CherryBlossomTreeBees005ConfiguredFeature());

            // TODO: Might be able to get this to work in JSON if I registered the cherry trees in a deferred register using registryobjects (though that doesnt like configuredfeatures passed in for some reason)...
            ModFeatures.CHERRY_TREE_FOREST = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_forest", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(ModFeatures.BIG_CHERRY_TREE.weighted(0.01f)), ModFeatures.CHERRY_TREE_BEES_002)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(6, 0.1f, 1))));

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
            ModFeatures.CHERRY_TREE_FOREST_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_forest_flowers", Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(CHERRY_BLOSSOM_FOREST_FLOWER_FEATURES)).count(FeatureSpread.of(-3, 4)).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(15));
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
            foliagePlacerRegistryEvent.getRegistry().register(new BigCherryBlossomFoliagePlacerType());
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
        public static TrunkPlacerType<BigCherryBlossomTrunkPlacer> BIG_CHERRY_TREE_TRUNK_PLACER;

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) throws InvocationTargetException, InstantiationException, IllegalAccessException {
            TrunkPlacerType<CherryBlossomTrunkPlacer> cherryBlossomTrunkPlacerType = CherryBlossomTrunkPlacerType.createTrunkPlacerType(CherryBlossomTrunkPlacer.CODEC);
            CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":cherry_blossom_tree_trunk_placer", cherryBlossomTrunkPlacerType);

            TrunkPlacerType<BigCherryBlossomTrunkPlacer> bigCherryBlossomTrunkPlacerType = BigCherryBlossomTrunkPlacerType.createTrunkPlacerType(BigCherryBlossomTrunkPlacer.CODEC);
            BIG_CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":big_cherry_blossom_tree_trunk_placer", bigCherryBlossomTrunkPlacerType);
        }
    }

    /**
     * Inner class for different event handlers overriding handlers from vanilla Minecraft
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
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
         * Used to handle events that occur when a chunk is loaded.
         * Currently this replaces any base game grass blocks with the mod's overridden grass block.
         *
         * @param event The event object that is built when a chunk is loaded
         */
        @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
        public static void onChunkEvent(ChunkEvent.Load event) {
            IChunk chunk = event.getChunk();

            if (chunk instanceof Chunk) {
                int chunkSize = chunk.getSections().length;
                if (event.getWorld().isClientSide()) {
                    for (int x = 0; x < chunkSize; ++x) {
                        for (int z = 0; z < chunkSize; ++z) {
                            for (int y = 0; y < chunk.getMaxBuildHeight(); ++y) {
                                if (chunk.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.GRASS_BLOCK) {
                                    //chunk.setBlockState(new BlockPos(x, y, z), ModBlocks.GRASS.defaultBlockState(), true);
                                }
                            }
                        }
                    }
                }
            }
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
    }
}
