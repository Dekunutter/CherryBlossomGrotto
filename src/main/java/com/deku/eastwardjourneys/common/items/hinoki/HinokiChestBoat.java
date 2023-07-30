package com.deku.eastwardjourneys.common.items.hinoki;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractChestBoatItem;

public class HinokiChestBoat extends AbstractChestBoatItem {
    public HinokiChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.HINOKI);
    }

}
