package com.deku.cherryblossomgrotto.common.items.water_fir;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractBoatItem;

public class WaterFirBoat extends AbstractBoatItem {
    public WaterFirBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.WATER_FIR);
    }
}
