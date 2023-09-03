package com.deku.eastwardjourneys.common.items.black_pine;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.items.AbstractChestBoatItem;

public class BlackPineChestBoat extends AbstractChestBoatItem {
    public BlackPineChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.BLACK_PINE);
    }

}
