package com.deku.cherryblossomgrotto.client.renderers;

import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class KunaiRenderer extends EntityRenderer<KunaiEntity> {
    private static final float RENDERED_SCALE = 0.05625F;
    private static final double RENDERED_LENGTH_X = 4;

    public KunaiRenderer(EntityRendererProvider.Context renderContext) {
        super(renderContext);
    }

    /**
     * Gets the location of the texture to be rendered by this renderable
     *
     * @param entity The entity the texture is being applied to
     * @return The location of the texture resource
     */
    @Override
    public ResourceLocation getTextureLocation(KunaiEntity entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/kunai.png");
    }

    /**
     * Renders the kunai entity into the world, applying its texture to its position.
     * This pushes a render stack into memory, performs all necessary operations and then pops it out of the stack
     * to pass it into a final renderer.
     *
     * @param entity The kunai entity to be rendered
     * @param yaw Rotation of the entity around the yaw axis
     * @param partialTicks The time through the current tick of the entity
     * @param poseStack The rendering stack that holds the pose information of the entity
     * @param buffer Render buffer
     * @param packedLight Light affecting the entity
     */
    @Override
    public void render(KunaiEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        renderShakingAnimation(entity, partialTicks, poseStack);

        poseStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        poseStack.scale(RENDERED_SCALE, RENDERED_SCALE, RENDERED_SCALE);
        poseStack.translate(-RENDERED_LENGTH_X, 0.0D, 0.0D);
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        PoseStack.Pose stackEntry = poseStack.last();
        Matrix4f positionMatrix = stackEntry.pose();
        Matrix3f normalMatrix = stackEntry.normal();

        renderRightSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);
        renderLeftSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);

        repositionForCrossRender(poseStack);

        renderRightSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);
        renderLeftSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    /**
     * Renders the kunai shaking once it collides with a block.
     *
     * @param entity The entity we are rendering
     * @param partialTicks The time through the current tick of the entity
     * @param poseStack The rendering stack that holds the pose information of the entity
     */
    private void renderShakingAnimation(KunaiEntity entity, float partialTicks, PoseStack poseStack) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        float f9 = (float)entity.shakeTime - partialTicks;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(f10));
        }
    }

    /**
     * Turns the rendering stack 90 degrees on the X axis pivot and translates downwards by half of the rendered entity's body
     * length on the Y and Z axises so that further rendering can result in a cross-style rendered 3D texture.
     *
     * @param poseStack The rendering stack that holds the pose information of the entity
     */
    private void repositionForCrossRender(PoseStack poseStack) {
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        poseStack.translate(0.0D, -(RENDERED_LENGTH_X / 2), -(RENDERED_LENGTH_X / 2));
    }

    /**
     * Renders the right side face of the entity.
     *
     * @param positionMatrix 4-dimensional matrix holding positional information on the entity
     * @param normalMatrix 3-dimensional matrix holding normal data about the entity for rendering
     * @param vertexBuilder Builder for the vertices
     * @param packedLight Light affecting the entity
     */
    private void renderRightSideFace(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexBuilder, int packedLight) {
        vertex(positionMatrix, normalMatrix, vertexBuilder, -4, 0, 0, 0.0F, 0.0F, 0, 1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, 4, 0, 0, 1.0F, 0.0F, 0, 1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, 4, 4, 0, 1.0F, 0.625F, 0, 1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, -4, 4, 0, 0.0F, 0.625F, 0, 1, 0, packedLight);
    }

    /**
     * Renders the left side face of the entity.
     *
     * @param positionMatrix 4-dimensional matrix holding positional information on the entity
     * @param normalMatrix 3-dimensional matrix holding normal data about the entity for rendering
     * @param vertexBuilder Builder for the vertices
     * @param packedLight Light affecting the entity
     */
    private void renderLeftSideFace(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexBuilder, int packedLight) {
        vertex(positionMatrix, normalMatrix, vertexBuilder, -4, 4, 0, 0.0F, 0.0F, 0, -1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, 4, 4, 0, 1.0F, 0.0F, 0, -1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, 4, 0, 0, 1.0F, 0.625F, 0, -1, 0, packedLight);
        vertex(positionMatrix, normalMatrix, vertexBuilder, -4, 0, 0, 0.0F, 0.625F, 0, -1, 0, packedLight);
    }

    /**
     * Builds a given vertex by attaching all positional and rendering data to it
     *
     * @param positionMatrix 4-dimensional matrix holding positional information on the entity
     * @param normalMatrix 3-dimensional matrix holding normal data about the entity for rendering
     * @param vertexBuilder Builder for this vertex
     * @param positionX Position along the X axis that we want to place this vertex
     * @param positionY Position along the Y axis that we want to place this vertex
     * @param positionZ Position along the Z axis that we want to place this vertex
     * @param horizontalUV Decimal number on how much of the texture we want to stretch across this vertex horizontally
     * @param verticalUV Decimal number on how much of the texture we want to stretch acorss this vertex vertically
     * @param normalX The normal value for the X axis
     * @param normalY The normal value for the Y axis
     * @param normalZ The normal value for the Z axis
     * @param lightingUV Value for mapping lighting data to the vertex
     */
    public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexBuilder, int positionX, int positionY, int positionZ, float horizontalUV, float verticalUV, int normalX, int normalY, int normalZ, int lightingUV) {
        vertexBuilder.vertex(positionMatrix, (float)positionX, (float)positionY, (float)positionZ).color(255, 255, 255, 255).uv(horizontalUV, verticalUV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightingUV).normal(normalMatrix, (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }
}
