package com.deku.eastwardjourneys.common.items.water_fir;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractChestBoatItem;

public class WaterFirChestBoat extends AbstractChestBoatItem {
    public WaterFirChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.WATER_FIR);
    }

}
