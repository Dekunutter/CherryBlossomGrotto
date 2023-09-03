package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.client.models.NinjaRobesModel;
import com.deku.eastwardjourneys.common.items.NinjaRobesItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class NinjaRobesRenderer extends GeoArmorRenderer<NinjaRobesItem> {
    public NinjaRobesRenderer() {
        super(new NinjaRobesModel());
    }
}
