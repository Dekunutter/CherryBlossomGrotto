package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.KabutoArmourModel;
import com.deku.cherryblossomgrotto.client.models.geom.ModModelLayerLocations;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KabutoArmourLayer extends HumanoidArmorLayer<LivingEntity, HumanoidModel<LivingEntity>, HumanoidModel<LivingEntity>> {
    public static KabutoArmourModel MODEL;

    public KabutoArmourLayer(RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>> armourRenderer, EntityModelSet modelSet) {
        super(armourRenderer, null, new HumanoidModel(modelSet.bakeLayer(ModModelLayerLocations.KABUTO_ARMOUR)));
        MODEL = new KabutoArmourModel(modelSet.bakeLayer(ModModelLayerLocations.KABUTO_ARMOUR));
    }
}
