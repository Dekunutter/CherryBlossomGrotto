package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.utils.LogTweaker;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
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
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
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
        }

        /**
         * Used to register items into the game using the mod event bus
         *
         * @param itemRegistryEvent The registry event with which items will be registered
         */
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            LOGGER.info("HELLO from Register Item");
        }

        /**
         * Used to register particle types into the game using the mod event bus
         *
         * @param particleTypeRegistryEvent The registry event with which particle types will be registered
         */
        @SubscribeEvent
        public static void onParticleTypeRegistry(final RegistryEvent.Register<ParticleType<?>> particleTypeRegistryEvent) {
            LOGGER.info("HELLO from Register Particle Type");
        }
    }
}
