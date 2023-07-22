package com.deku.cherryblossomgrotto.common.items.saxaul;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class SaxaulChestBoat extends AbstractChestBoatItem {
    public SaxaulChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.SAXAUL);
    }

}
