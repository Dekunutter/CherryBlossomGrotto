package com.deku.eastwardjourneys;

import com.deku.eastwardjourneys.client.models.geom.ModLayerDefinitions;
import com.deku.eastwardjourneys.client.models.geom.ModModelLayerLocations;
import com.deku.eastwardjourneys.client.renderers.*;
import com.deku.eastwardjourneys.common.blocks.ModWoodType;
import com.deku.eastwardjourneys.common.entity.ModEntityTypeInitializer;
import com.deku.eastwardjourneys.common.entity.ModBlockEntities;
import com.deku.eastwardjourneys.common.items.ModItems;
import com.deku.eastwardjourneys.common.particles.FallingMapleLeafProvider;
import com.deku.eastwardjourneys.common.particles.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib.GeckoLib;

import static com.deku.eastwardjourneys.Main.MOD_ID;


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

        GeckoLib.initialize();
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
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModWoodType.MAPLE);
            Sheets.addWoodType(ModWoodType.BLACK_PINE);
            Sheets.addWoodType(ModWoodType.HINOKI);
            Sheets.addWoodType(ModWoodType.WATER_FIR);
            Sheets.addWoodType(ModWoodType.SAXAUL);
        });
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientRegistryEvents {
        /**
         * Used to register entity renderers into the game using the mod event bus
         *
         * @param registerEntityEvent The registry event with which entity renderers will be registered
         */
        @SubscribeEvent
        public static void onEntityRendererRegistry(final EntityRenderersEvent.RegisterRenderers registerEntityEvent) {
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.BOAT_ENTITY_TYPE.get(), ModBoatRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.CHEST_BOAT_ENTITY_TYPE.get(), ModChestBoatRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.KOI_ENTITY_TYPE.get(), KoiRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), TanookiRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.TERRACOTTA_WARRIOR_ENTITY_TYPE.get(), TerracottaWarriorRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.KUNAI_ENTITY_TYPE.get(), KunaiRenderer::new);
            registerEntityEvent.registerEntityRenderer(ModEntityTypeInitializer.SHURIKEN_ENTITY_TYPE.get(), ShurikenRenderer::new);

            registerEntityEvent.registerBlockEntityRenderer(ModBlockEntities.SIGN_ENTITY_TYPE, SignRenderer::new);
            registerEntityEvent.registerBlockEntityRenderer(ModBlockEntities.HANGING_SIGN_ENTITY_TYPE, HangingSignRenderer::new);
        }

        /**
         * Used to register entity layer definitions into the game using the mod event bus
         * All new entity layers need to be defined here to be loaded into the game, including those rendering for vanilla entities
         *
         * @param registerLayerDefinitionEvent The registry event with which entity layer definitions will be registered
         */
        @SubscribeEvent
        public static void onEntityRendererRegistry(final EntityRenderersEvent.RegisterLayerDefinitions registerLayerDefinitionEvent) {
            registerLayerDefinitionEvent.registerLayerDefinition(ModModelLayerLocations.KOI, () -> ModLayerDefinitions.KOI_LAYER);
        }

        /**
         * Used to register block color handlers into the game using the mod event bus
         *
         * @param event The registry event with which block color handlers will be registered
         */
        @SubscribeEvent
        public static void onBlockColorHandlerRegistration(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();
            //blockColors.register(GrassBlockColor.instance, ModBlocks.GRASS);
        }

        /**
         * Used to register particle factories into the game using the mod event bus
         *
         * @param particleFactoryRegistryEvent The registry event with which particle factories will be registered
         */
        @SubscribeEvent
        public static void onParticleFactoryRegistry(final RegisterParticleProvidersEvent particleFactoryRegistryEvent) {
            Minecraft.getInstance().particleEngine.register(ModParticles.MAPLE_LEAF, FallingMapleLeafProvider::new);
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
                event.getRenderer().getModel().rightArm.visible = true;
            }

            @SubscribeEvent
            public static void onRenderPlayerEvent(RenderHandEvent event) {
                if (event.getItemStack().getItem() == ModItems.SHURIKEN) {
                }
            }
        }
    }
}
