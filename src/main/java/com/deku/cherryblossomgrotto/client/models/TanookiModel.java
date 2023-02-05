package com.deku.cherryblossomgrotto.client.models;

import com.deku.cherryblossomgrotto.common.entity.animal.tanooki.Tanooki;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class TanookiModel extends DefaultedEntityGeoModel<Tanooki> {
    public TanookiModel() {
        super(new ResourceLocation(MOD_ID, "animals/tanooki"), false);
    }
}
