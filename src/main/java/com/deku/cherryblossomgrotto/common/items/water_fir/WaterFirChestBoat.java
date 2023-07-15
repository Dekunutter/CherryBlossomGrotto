package com.deku.cherryblossomgrotto.common.items.water_fir;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class WaterFirChestBoat extends AbstractChestBoatItem {
    public WaterFirChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.WATER_FIR);
    }

}
