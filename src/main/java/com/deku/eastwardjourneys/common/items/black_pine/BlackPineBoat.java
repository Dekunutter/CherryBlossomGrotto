package com.deku.eastwardjourneys.common.items.black_pine;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractBoatItem;

public class BlackPineBoat extends AbstractBoatItem {
    public BlackPineBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.BLACK_PINE);
    }
}
