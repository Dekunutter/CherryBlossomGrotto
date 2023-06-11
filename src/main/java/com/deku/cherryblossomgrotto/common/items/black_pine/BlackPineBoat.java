package com.deku.cherryblossomgrotto.common.items.black_pine;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractBoatItem;

public class BlackPineBoat extends AbstractBoatItem {
    public BlackPineBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.BLACK_PINE);
    }
}
