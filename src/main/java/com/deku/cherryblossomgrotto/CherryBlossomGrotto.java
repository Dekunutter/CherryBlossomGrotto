package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.common.blocks.*;
import com.deku.cherryblossomgrotto.common.features.CherryBlossomTreeFeature;
import com.deku.cherryblossomgrotto.common.features.LargeCherryBlossomTreeFeature;
import com.deku.cherryblossomgrotto.common.features.ModFeatures;
import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalFactory;
import com.deku.cherryblossomgrotto.common.particles.ModParticles;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryLeavesTileEntity;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacerType;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlaceType;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.utils.LogTweaker;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CherryBlossomGrotto.MOD_ID)
public class CherryBlossomGrotto
{
    // TODO: Set to true to hide noise on console when mod is finished
    final boolean HIDE_CONSOLE_NOISE = false;

    // declare Mod ID
    public static final String MOD_ID = "cherryblossomgrotto";

    // Initialize logger
    public static final Logger LOGGER = LogManager.getLogger(CherryBlossomGrotto.class);

    /**
     * Constructor for initializing the mod.
     * Handles the setup of:
     *      - Log filtering.
     *      - Event Bus listeners
     *      - Registries
     *      - Ensuring client-only registrars only execute on a client
     */
    public CherryBlossomGrotto() {
        if (HIDE_CONSOLE_NOISE) {
            LogTweaker.applyLogFilterLevel(Level.WARN);
        }
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        ClientOnlyRegistrar clientOnlyRegistrar = new ClientOnlyRegistrar(eventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientOnlyRegistrar::registerClientOnlyEvents);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
    }

    /**
     * Sets up client specific logic, such as rendering types.
     *
     * In this case we are adding the cut-out render type to our custom grass block so
     * that overlays display properly on that block.
     *
     * @param event The client setup event
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);

        //RenderTypeLookup.setRenderLayer(ModBlocks.GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_PETALS, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHERRY_SAPLING, RenderType.cutoutMipped());
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

            blockRegistryEvent.getRegistry().register(new CherryBlossomLeaves());

            // TODO: Commented out overridden grass block for possible removal in the future. Find all instances and remove if block goes unused
            //blockRegistryEvent.getRegistry().register(new NewGrassBlock());
            blockRegistryEvent.getRegistry().register(new CherryBlossomPetals());

            blockRegistryEvent.getRegistry().register(new CherryBlossomSapling());

            //NOTE: Just a test on overriding base game blocks directly in the registry
            //blockRegistryEvent.getRegistry().register(new DirtTest());
        }

        public static TileEntityType<CherryLeavesTileEntity> cherryLeavesDataType;

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> tileEntityRegistryEvent) {
            cherryLeavesDataType = TileEntityType.Builder.of(CherryLeavesTileEntity::new, ModBlocks.CHERRY_LEAVES.getBlock()).build(null);
            cherryLeavesDataType.setRegistryName("cherryblossomgrotto:cherry_leaves_tile_entity");
            tileEntityRegistryEvent.getRegistry().register(cherryLeavesDataType);
        }

        /**
         * Used to register items into the game using the mod event bus
         *
         * @param itemRegistryEvent The registry event with which items will be registered
         */
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            LOGGER.info("HELLO from Register Item");

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_LOG, new Item.Properties()).setRegistryName("cherry_blossom_log"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.STRIPPED_CHERRY_LOG, new Item.Properties()).setRegistryName("stripped_cherry_blossom_log"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_WOOD, new Item.Properties()).setRegistryName("cherry_blossom_wood"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.STRIPPED_CHERRY_WOOD, new Item.Properties()).setRegistryName("stripped_cherry_blossom_wood"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_PLANKS, new Item.Properties()).setRegistryName("cherry_blossom_planks"));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_LEAVES, new Item.Properties()).setRegistryName("cherry_blossom_leaves"));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_PETALS, new Item.Properties()).setRegistryName("cherry_blossom_petals"));
            //itemRegistryEvent.getRegistry().register(new BlockItem(OverriddenBlocks.DIRT, new Item.Properties()).setRegistryName("dirt"));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHERRY_SAPLING, new Item.Properties()).setRegistryName("cherry_blossom_sapling"));
        }

        /**
         * Used to register features into the game using the mod event bus
         *
         * @param featureRegistryEvent The registry event with which features will be registered
         */
        @SubscribeEvent
        public static void onFeaturesRegistry(final RegistryEvent.Register<Feature<?>> featureRegistryEvent) {
            LOGGER.info("HELLO from Register Feature");

            featureRegistryEvent.getRegistry().register(new CherryBlossomTreeFeature(false));
            featureRegistryEvent.getRegistry().register(new LargeCherryBlossomTreeFeature(false));
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
            CherryBlossomGrotto.LOGGER.info("HELLO from Register Particle Factory");

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
     * Inner class for world generation feature event registers.
     * Foliage placers take parameters for adding additional offsets to the foliage's height and specifying the desired radius.
     * Trunk placers take in a base height and two additional randomizers which will add to the overall height for extra randomness.
     * TwoLayerFeature generates a loose "boundary" around other generated features to ensure that trees don't form too close to others
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class WorldGenRegistryEventHandler {
        public static ConfiguredFeature<BaseTreeFeatureConfig, ?> CHERRY_TREE;

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            BaseTreeFeatureConfig cherryBlossomConfig = (new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                    new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                    new CherryBlossomFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0)),
                    new CherryBlossomTrunkPlacer(4, 2, 2),
                    new TwoLayerFeature(1, 0, 2))
            ).ignoreVines().build();
            CHERRY_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MOD_ID + ":cherry_blossom_tree_config", Feature.TREE.configured(cherryBlossomConfig));
        }
    }

    /**
     * Inner class for different event registers used by the mod for world generation
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class TrunkPlacerRegistryEventHandler {
        public static TrunkPlacerType<CherryBlossomTrunkPlacer> CHERRY_TREE_TRUNK_PLACER;

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) throws InvocationTargetException, InstantiationException, IllegalAccessException {
            TrunkPlacerType<CherryBlossomTrunkPlacer> trunkPlacerType = CherryBlossomTrunkPlaceType.createTrunkPlacerType(CherryBlossomTrunkPlacer.CODEC);
            CHERRY_TREE_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, MOD_ID + ":cherry_blossom_tree_trunk_placer", trunkPlacerType);
        }
    }

    /**
     * Inner class for different event handlers overriding handlers from vanilla Minecraft
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
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
