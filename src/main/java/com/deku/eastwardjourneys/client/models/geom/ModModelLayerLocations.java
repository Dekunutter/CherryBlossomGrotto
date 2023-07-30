package com.deku.eastwardjourneys.client.models.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModModelLayerLocations {
    public static ModelLayerLocation KOI = ModModelLayerLocations.getLayerLocation("koi");
    public static ModelLayerLocation KABUTO_ARMOUR = ModModelLayerLocations.getLayerLocation("kabuto_armour");
    public static ModelLayerLocation INNER_KABUTO_ARMOUR = ModModelLayerLocations.getLayerLocation("inner_kabuto_armour");

    public static ModelLayerLocation NINJA_ROBES = ModModelLayerLocations.getLayerLocation("ninja_robes");
    public static ModelLayerLocation INNER_NINJA_ROBES = ModModelLayerLocations.getLayerLocation("inner_ninja_robes");

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
