package com.deku.cherryblossomgrotto.client.models.geom;

import com.deku.cherryblossomgrotto.client.models.KabutoArmourModel;
import com.deku.cherryblossomgrotto.client.models.KoiModel;
import com.deku.cherryblossomgrotto.client.models.NinjaRobesModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ModLayerDefinitions {
    public static LayerDefinition KOI_LAYER = KoiModel.createBodyLayer();

    public static LayerDefinition KABUTO_ARMOUR_LAYER = KabutoArmourModel.createBodyLayer();

    public static LayerDefinition NINJA_ROBES_LAYER = NinjaRobesModel.createBodyLayer();
}
