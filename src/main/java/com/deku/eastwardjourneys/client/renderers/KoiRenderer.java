package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.client.models.KoiModel;
import com.deku.eastwardjourneys.client.models.geom.ModModelLayerLocations;
import com.deku.eastwardjourneys.client.renderers.layers.KoiPatternLayer;
import com.deku.eastwardjourneys.common.entity.passive.fish.KoiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.eastwardjourneys.Main.MOD_ID;

// NOTE: Since kois are just re-skinned salmons I'm just doing a flat copy of the logic in the vanilla salmon renderer for now to save time
@OnlyIn(Dist.CLIENT)
public class KoiRenderer extends MobRenderer<KoiEntity, ColorableHierarchicalModel<KoiEntity>> {

    public KoiRenderer(EntityRendererProvider.Context renderContext) {
        super(renderContext, new KoiModel<KoiEntity>(renderContext.bakeLayer(ModModelLayerLocations.KOI)), 0.4F);
        this.addLayer(new KoiPatternLayer(this, renderContext.getModelSet()));
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
     * @param poseStack The rendering stack that holds the pose information of the model
     * @param rotX Rotation of the entity along the X axis
     * @param rotY Rotation of the entity along the Y axis
     * @param rotZ Rotation of the entity along the Z axis
     */
    protected void setupRotations(KoiEntity entity, PoseStack poseStack, float rotX, float rotY, float rotZ) {
        super.setupRotations(entity, poseStack, rotX, rotY, rotZ);
        float f = 1.0F;
        float f1 = 1.0F;
        if (!entity.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        float f2 = f * 4.3F * Mth.sin(f1 * 0.6F * rotX);
        poseStack.mulPose(Axis.YP.rotationDegrees(f2));
        poseStack.translate(0.0D, 0.0D, (double)-0.4F);
        if (!entity.isInWater()) {
            poseStack.translate((double)0.2F, (double)0.1F, 0.0D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
