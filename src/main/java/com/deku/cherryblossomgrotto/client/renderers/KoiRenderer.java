package com.deku.cherryblossomgrotto.client.renderers;

import com.deku.cherryblossomgrotto.client.models.KoiModel;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

// NOTE: Since kois are just re-skinned salmons I'm just doing a flat copy of the logic in the vanilla salmon renderer for now to save time
@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<KoiEntity, KoiModel<KoiEntity>> {

    public KoiRenderer(EntityRendererManager renderManager) {
        super(renderManager, new KoiModel<>(), 0.4F);
    }

    /**
     * Gets the resource location of the texture being rendered on the koi entity
     *
     * @param entity Entity that the texture is being loaded for
     * @return The resource location of the texture for this entity
     */
    @Override
    public ResourceLocation getTextureLocation(KoiEntity entity) {
        return new ResourceLocation(MOD_ID, "textures/entity/fish/koi.png");
    }

    /**
     * Sets up the rotations used by this entity
     *
     * @param entity The entity being rotated
     * @param matrixStack The rendering stack that holds the rendering co-ordinates of the entity
     * @param rotX Rotation of the entity along the X axis
     * @param rotY Rotation of the entity along the Y axis
     * @param rotZ Rotation of the entity along the Z axis
     */
    protected void setupRotations(KoiEntity entity, MatrixStack matrixStack, float rotX, float rotY, float rotZ) {
        super.setupRotations(entity, matrixStack, rotX, rotY, rotZ);
        float f = 1.0F;
        float f1 = 1.0F;
        if (!entity.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        float f2 = f * 4.3F * MathHelper.sin(f1 * 0.6F * rotX);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f2));
        matrixStack.translate(0.0D, 0.0D, (double)-0.4F);
        if (!entity.isInWater()) {
            matrixStack.translate((double)0.2F, (double)0.1F, 0.0D);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }
}
