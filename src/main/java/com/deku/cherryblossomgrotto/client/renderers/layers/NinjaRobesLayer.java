package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.NinjaRobesModel;
import com.deku.cherryblossomgrotto.client.models.geom.ModModelLayerLocations;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NinjaRobesLayer extends HumanoidArmorLayer<LivingEntity, HumanoidModel<LivingEntity>, HumanoidModel<LivingEntity>> {
    public static NinjaRobesModel MODEL;

    public NinjaRobesLayer(RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>> armourRenderer, EntityModelSet modelSet) {
        super(armourRenderer, null, new HumanoidModel<>(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES)));
        MODEL = new NinjaRobesModel(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES));
    }
}
