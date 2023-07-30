package com.deku.eastwardjourneys.common.items.water_fir;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractBoatItem;

public class WaterFirBoat extends AbstractBoatItem {
    public WaterFirBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.WATER_FIR);
    }
}
