package com.deku.cherryblossomgrotto.client.models.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModModelLayerLocations {
    public static ModelLayerLocation KOI = ModModelLayerLocations.getLayerLocation("koi");

    public static ModelLayerLocation getLayerLocation(String layerName) {
        return new ModelLayerLocation(new ResourceLocation(MOD_ID, layerName), "main");
    }
}
