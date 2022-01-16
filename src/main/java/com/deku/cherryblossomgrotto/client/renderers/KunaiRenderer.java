package com.deku.cherryblossomgrotto.client.renderers;

import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class KunaiRenderer extends EntityRenderer<KunaiEntity> {
    private static final float RENDERED_SCALE = 0.05625F;
    private static final double RENDERED_LENGTH_X = 4;

    public KunaiRenderer(EntityRendererManager renderManager) {
        super(renderManager);
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
     * @param matrixStack The rendering stack that holds the rendering co-ordinates of the entity
     * @param buffer Render buffer
     * @param packedLight Light affecting the entity
     */
    @Override
    public void render(KunaiEntity entity, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();

        renderShakingAnimation(entity, partialTicks, matrixStack);

        matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        matrixStack.scale(RENDERED_SCALE, RENDERED_SCALE, RENDERED_SCALE);
        matrixStack.translate(-RENDERED_LENGTH_X, 0.0D, 0.0D);
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        MatrixStack.Entry stackEntry = matrixStack.last();
        Matrix4f positionMatrix = stackEntry.pose();
        Matrix3f normalMatrix = stackEntry.normal();

        renderRightSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);
        renderLeftSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);

        repositionForCrossRender(matrixStack);

        renderRightSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);
        renderLeftSideFace(positionMatrix, normalMatrix, vertexBuilder, packedLight);

        matrixStack.popPose();
        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
    }

    /**
     * Renders the kunai shaking once it collides with a block.
     *
     * @param entity The entity we are rendering
     * @param partialTicks The time through the current tick of the entity
     * @param matrixStack The rendering stack that holds the rendering co-ordinates of the entity
     */
    private void renderShakingAnimation(KunaiEntity entity, float partialTicks, MatrixStack matrixStack) {
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot)));
        float f9 = (float)entity.shakeTime - partialTicks;
        if (f9 > 0.0F) {
            float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f10));
        }
    }

    /**
     * Turns the rendering stack 90 degrees on the X axis pivot and translates downwards by half of the rendered entity's body
     * length on the Y and Z axises so that further rendering can result in a cross-style rendered 3D texture.
     *
     * @param matrixStack The rendering stack that holds the rendering co-ordinates of the entity
     */
    private void repositionForCrossRender(MatrixStack matrixStack) {
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStack.translate(0.0D, -(RENDERED_LENGTH_X / 2), -(RENDERED_LENGTH_X / 2));
    }

    /**
     * Renders the right side face of the entity.
     *
     * @param positionMatrix 4-dimensional matrix holding positional information on the entity
     * @param normalMatrix 3-dimensional matrix holding normal data about the entity for rendering
     * @param vertexBuilder Builder for the vertices
     * @param packedLight Light affecting the entity
     */
    private void renderRightSideFace(Matrix4f positionMatrix, Matrix3f normalMatrix, IVertexBuilder vertexBuilder, int packedLight) {
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
    private void renderLeftSideFace(Matrix4f positionMatrix, Matrix3f normalMatrix, IVertexBuilder vertexBuilder, int packedLight) {
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
    public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, IVertexBuilder vertexBuilder, int positionX, int positionY, int positionZ, float horizontalUV, float verticalUV, int normalX, int normalY, int normalZ, int lightingUV) {
        vertexBuilder.vertex(positionMatrix, (float)positionX, (float)positionY, (float)positionZ).color(255, 255, 255, 255).uv(horizontalUV, verticalUV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightingUV).normal(normalMatrix, (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }
}
