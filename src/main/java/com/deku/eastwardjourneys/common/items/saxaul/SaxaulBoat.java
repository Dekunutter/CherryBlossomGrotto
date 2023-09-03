package com.deku.eastwardjourneys.common.items.saxaul;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractBoatItem;

public class SaxaulBoat extends AbstractBoatItem {
    public SaxaulBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.SAXAUL);
    }
}
