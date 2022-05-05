package com.deku.cherryblossomgrotto;

import com.deku.cherryblossomgrotto.client.renderers.KoiRenderer;
import com.deku.cherryblossomgrotto.client.renderers.KunaiRenderer;
import com.deku.cherryblossomgrotto.client.renderers.ModBoatRenderer;
import com.deku.cherryblossomgrotto.client.renderers.ShurikenRenderer;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.blocks.ModWoodType;
import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import com.deku.cherryblossomgrotto.common.tileEntities.ModTileEntityData;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.deku.cherryblossomgrotto.Main.LOGGER;
import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ClientOnlyRegistrar {
    private IEventBus eventBus;

    public ClientOnlyRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Registers any events that must ONLY be registered on a client.
     * This may include things like graphics rendering, user input processing, etc.
     */
    public void registerClientOnlyEvents() {
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);
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

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientRegistryEvents {
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

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientEventHandler {
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
    }
}
