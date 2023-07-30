package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.client.models.TanookiModel;
import com.deku.eastwardjourneys.common.entity.animal.tanooki.Tanooki;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class TanookiRenderer extends GeoEntityRenderer<Tanooki> {
    public TanookiRenderer(EntityRendererProvider.Context context) {
        super(context, new TanookiModel());
    }

    /**
     * The initial access point for rendering a model.
     * Overridden here so that we can manipulate the model by scaling it down if it's a baby
     *
     * @param poseStack The poses of the model
     * @param animatable The animatable entity that's being rendered
     * @param bufferSource Rendering buffer
     * @param renderType Type of rendering applied to this model
     * @param buffer Builder for the vertices
     * @param yaw Vertical rotation of the model
     * @param partialTick The time through the current tick of the entity
     * @param packedLight Light affecting the entity
     */
    @Override
    public void defaultRender(PoseStack poseStack, Tanooki animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer,
                               float yaw, float partialTick, int packedLight) {
        if (animatable.isBaby()) {
            // TODO: Scale the head on the model separately so its bigger than the body, like other minecraft baby models do
            poseStack.scale(0.4F, 0.4F, 0.4F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }
}
