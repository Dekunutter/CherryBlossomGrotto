package com.deku.eastwardjourneys.common.items.hinoki;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractBoatItem;

public class HinokiBoat extends AbstractBoatItem {
    public HinokiBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.HINOKI);
    }
}
