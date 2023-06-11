package com.deku.cherryblossomgrotto.common.items.black_pine;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class BlackPineChestBoat extends AbstractChestBoatItem {
    public BlackPineChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.BLACK_PINE);
    }

}
