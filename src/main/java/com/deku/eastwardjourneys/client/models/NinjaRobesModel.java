package com.deku.eastwardjourneys.client.models;

import com.deku.eastwardjourneys.common.items.NinjaRobesItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class NinjaRobesModel extends GeoModel<NinjaRobesItem> {
	@Override
	public ResourceLocation getModelResource(NinjaRobesItem animatable) {
		return new ResourceLocation(MOD_ID, "geo/model/ninja_robes.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NinjaRobesItem animatable) {
		return new ResourceLocation(MOD_ID, "textures/models/armor/ninja_robes.png");
	}

	@Override
	public ResourceLocation getAnimationResource(NinjaRobesItem animatable) {
		return new ResourceLocation(MOD_ID, "animations/model/ninja_robes.animation.json");
	}
}