package com.deku.eastwardjourneys.common.items.maple;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractChestBoatItem;

public class MapleChestBoat extends AbstractChestBoatItem {
    public MapleChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.MAPLE);
    }
}
