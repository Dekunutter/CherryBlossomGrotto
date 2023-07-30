package com.deku.eastwardjourneys.client.renderers.layers;

import com.deku.eastwardjourneys.client.models.KoiModel;
import com.deku.eastwardjourneys.client.models.geom.ModModelLayerLocations;
import com.deku.eastwardjourneys.common.entity.passive.fish.KoiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiPatternLayer extends RenderLayer<KoiEntity, ColorableHierarchicalModel<KoiEntity>> {
    private final KoiModel<KoiEntity> model;

    public KoiPatternLayer(RenderLayerParent<KoiEntity, ColorableHierarchicalModel<KoiEntity>> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);
        model = new KoiModel<KoiEntity>(modelSet.bakeLayer(ModModelLayerLocations.KOI));
    }

    /**
     * Renders the cut-out layer over the base texture of the koi entity.
     * The last three integers in the coloredCutoutModelCopyLayerRender function will specify the brightness of the layered texture (0 for black, 1 for full colour)
     *
     * @param poseStack The rendering stack that holds the information about the model's pose
     * @param bufferSource Rendering buffer
     * @param p_225628_3_
     * @param entity The entity the layer texture is being applied to
     * @param p_225628_5_
     * @param p_225628_6_
     * @param p_225628_7_
     * @param p_225628_8_
     * @param p_225628_9_
     * @param p_225628_10_
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_225628_3_, KoiEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        coloredCutoutModelCopyLayerRender(getParentModel(), model, entity.getPatternTextureLocation(), poseStack, bufferSource, p_225628_3_, entity, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1, 1, 1);
    }
}
