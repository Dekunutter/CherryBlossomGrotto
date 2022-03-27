package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.KoiModel;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KoiPatternLayer extends LayerRenderer<KoiEntity, EntityModel<KoiEntity>> {
    private final KoiModel<KoiEntity> model = new KoiModel<>(0.008F);

    public KoiPatternLayer(IEntityRenderer<KoiEntity, EntityModel<KoiEntity>> entityRenderer) {
        super(entityRenderer);
    }

    /**
     * Renders the cut-out layer over the base texture of the koi entity.
     * The last three integers in the coloredCutoutModelCopyLayerRender function will specify the brightness of the layered texture (0 for black, 1 for full colour)
     *
     * @param matrixStack The rendering stack that holds the rendering co-ordinates of the entity
     * @param renderTypeBuffer Rendering buffer
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
    public void render(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int p_225628_3_, KoiEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        coloredCutoutModelCopyLayerRender(getParentModel(), model, entity.getPatternTextureLocation(), matrixStack, renderTypeBuffer, p_225628_3_, entity, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1, 1, 1);
    }
}
