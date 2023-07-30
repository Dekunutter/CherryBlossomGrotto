package com.deku.eastwardjourneys.client.models.geom;

import com.deku.eastwardjourneys.client.models.*;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ModLayerDefinitions {
    public static LayerDefinition KOI_LAYER = KoiModel.createBodyLayer();

    public static LayerDefinition KABUTO_ARMOUR_LAYER = KabutoArmourModel.createBodyLayer();
    public static LayerDefinition INNER_KABUTO_ARMOUR_LAYER = InnerKabutoArmourModel.createBodyLayer();

    public static LayerDefinition NINJA_ROBES_LAYER = NinjaRobesModel.createBodyLayer();
    public static LayerDefinition INNER_NINJA_ROBES_LAYER = InnerNinjaRobesModel.createBodyLayer();
}
