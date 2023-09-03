package com.deku.eastwardjourneys.client.models;

import com.deku.eastwardjourneys.common.entity.animal.tanooki.Tanooki;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class TanookiModel extends DefaultedEntityGeoModel<Tanooki> {
    public TanookiModel() {
        super(new ResourceLocation(MOD_ID, "animals/tanooki"), false);
    }
}
