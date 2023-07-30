package com.deku.eastwardjourneys.common.items.maple;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractBoatItem;

public class MapleBoat extends AbstractBoatItem {
    public MapleBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.MAPLE);
    }
}
