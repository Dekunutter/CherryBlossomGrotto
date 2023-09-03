package com.deku.eastwardjourneys.common.items.saxaul;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractChestBoatItem;

public class SaxaulChestBoat extends AbstractChestBoatItem {
    public SaxaulChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.SAXAUL);
    }

}
