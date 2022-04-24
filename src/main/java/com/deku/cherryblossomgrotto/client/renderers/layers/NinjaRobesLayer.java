package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.NinjaRobesModel;
import com.deku.cherryblossomgrotto.client.models.geom.ModModelLayerLocations;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NinjaRobesLayer extends HumanoidArmorLayer<Player, HumanoidModel<Player>, HumanoidModel<Player>> {
    // TODO: Use separate inner and outer models so that pants are layered as a separate armour object under the body armour
    public static NinjaRobesModel MODEL;

    public NinjaRobesLayer(RenderLayerParent<Player, HumanoidModel<Player>> armourRenderer, EntityModelSet modelSet) {
        super(armourRenderer, new HumanoidModel<>(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES)), new HumanoidModel<>(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES)));
        MODEL = new NinjaRobesModel(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES));
    }

    // TODO: Swap this (and the kabuto layer class) to use generics so that I dont need to redeclare logic like the property copier and part visibility handler
    @Override
    public void render(PoseStack matrix, MultiBufferSource renderer, int packedLightIn, Player entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        renderArmourPart(matrix, renderer, entity, EquipmentSlot.CHEST, packedLightIn, partialTicks);
        renderArmourPart(matrix, renderer, entity, EquipmentSlot.LEGS, packedLightIn, partialTicks);
        renderArmourPart(matrix, renderer, entity, EquipmentSlot.FEET, packedLightIn, partialTicks);
        renderArmourPart(matrix, renderer, entity, EquipmentSlot.HEAD, packedLightIn, partialTicks);
    }

    private void renderArmourPart(PoseStack matrix, MultiBufferSource renderer, Player entity, EquipmentSlot slot, int light, float partialTicks) {
        ItemStack itemStack = entity.getItemBySlot(slot);
        Item item = itemStack.getItem();

        if (item instanceof ArmorItem armourPiece && armourPiece.getSlot() == slot) {
            setPartVisibility(MODEL, slot);
            MODEL.applyEntityStats(getParentModel());

            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderer, RenderType.armorCutoutNoCull(MODEL.texture), false, itemStack.hasFoil());
            MODEL.renderToBuffer(matrix, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 255.0F, 255.0F, 255.0F, 255.0F);
        }
    }

    protected void setPartVisibility(NinjaRobesModel model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch(slot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
                break;
            case LEGS:
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }
    }
}
