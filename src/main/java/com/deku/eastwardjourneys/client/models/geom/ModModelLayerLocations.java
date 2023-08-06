package com.deku.eastwardjourneys.client.models.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModModelLayerLocations {
    public static ModelLayerLocation KOI = ModModelLayerLocations.getLayerLocation("koi");
    /**
     * Gets the model layer location for the given layer name
     *
     * @param layerName The name of the layer we want the model layer location for
     * @return The model layer location for the given layer
     */
    public static ModelLayerLocation getLayerLocation(String layerName) {
        return new ModelLayerLocation(new ResourceLocation(MOD_ID, layerName), "main");
    }
}
