package com.deku.cherryblossomgrotto.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// NOTE: Since kois are just re-skinned salmons I'm just doing a flat copy of the logic in the vanilla salmon model for now to save time
@OnlyIn(Dist.CLIENT)
public class KoiModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer bodyFront;
    private final ModelRenderer bodyBack;
    private final ModelRenderer head;
    private final ModelRenderer sideFin0;
    private final ModelRenderer sideFin1;

    public KoiModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        int i = 20;
        this.bodyFront = new ModelRenderer(this, 0, 0);
        this.bodyFront.addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
        this.bodyFront.setPos(0.0F, 20.0F, 0.0F);
        this.bodyBack = new ModelRenderer(this, 0, 13);
        this.bodyBack.addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
        this.bodyBack.setPos(0.0F, 20.0F, 8.0F);
        this.head = new ModelRenderer(this, 22, 0);
        this.head.addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F);
        this.head.setPos(0.0F, 20.0F, 0.0F);
        ModelRenderer modelrenderer = new ModelRenderer(this, 20, 10);
        modelrenderer.addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F);
        modelrenderer.setPos(0.0F, 0.0F, 8.0F);
        this.bodyBack.addChild(modelrenderer);
        ModelRenderer modelrenderer1 = new ModelRenderer(this, 2, 1);
        modelrenderer1.addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F);
        modelrenderer1.setPos(0.0F, -4.5F, 5.0F);
        this.bodyFront.addChild(modelrenderer1);
        ModelRenderer modelrenderer2 = new ModelRenderer(this, 0, 2);
        modelrenderer2.addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F);
        modelrenderer2.setPos(0.0F, -4.5F, -1.0F);
        this.bodyBack.addChild(modelrenderer2);
        this.sideFin0 = new ModelRenderer(this, -4, 0);
        this.sideFin0.addBox(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
        this.sideFin0.setPos(-1.5F, 21.5F, 0.0F);
        this.sideFin0.zRot = (-(float)Math.PI / 4F);
        this.sideFin1 = new ModelRenderer(this, 0, 0);
        this.sideFin1.addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
        this.sideFin1.setPos(1.5F, 21.5F, 0.0F);
        this.sideFin1.zRot = ((float)Math.PI / 4F);
    }

    /**
     * Gets a list of all the individual parts tied to this model.
     *
     * @return Returns an iterable and immutable list of all model renderers for this model
     */
    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.bodyFront, this.bodyBack, this.head, this.sideFin0, this.sideFin1);
    }

    /**
     * Sets up the animations for this model.
     *
     * @param entity The entity being animated
     * @param p_225597_2_
     * @param p_225597_3_
     * @param p_225597_4_
     * @param p_225597_5_
     * @param p_225597_6_
     */
    @Override
    public void setupAnim(T entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        float f = 1.0F;
        float f1 = 1.0F;
        if (!entity.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        this.bodyBack.yRot = -f * 0.25F * MathHelper.sin(f1 * 0.6F * p_225597_4_);
    }
}
