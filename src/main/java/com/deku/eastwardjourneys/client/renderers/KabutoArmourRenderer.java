package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.client.models.KabutoArmourModel;
import com.deku.eastwardjourneys.common.items.KabutoArmourItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class KabutoArmourRenderer extends GeoArmorRenderer<KabutoArmourItem> {
    public KabutoArmourRenderer() {
        super(new KabutoArmourModel());
    }
}
