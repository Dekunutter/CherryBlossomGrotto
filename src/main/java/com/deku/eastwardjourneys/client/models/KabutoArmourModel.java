package com.deku.eastwardjourneys.client.models;

import com.deku.eastwardjourneys.common.items.KabutoArmourItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class KabutoArmourModel extends GeoModel<KabutoArmourItem> {
	@Override
	public ResourceLocation getModelResource(KabutoArmourItem animatable) {
		return new ResourceLocation(MOD_ID, "geo/model/kabuto_armour.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(KabutoArmourItem animatable) {
		return new ResourceLocation(MOD_ID, "textures/models/armor/kabuto_armour.png");
	}

	@Override
	public ResourceLocation getAnimationResource(KabutoArmourItem animatable) {
		return new ResourceLocation(MOD_ID, "animations/model/kabuto_armour.animation.json");
	}
}