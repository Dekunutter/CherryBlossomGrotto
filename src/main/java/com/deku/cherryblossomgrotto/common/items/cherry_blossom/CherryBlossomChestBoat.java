package com.deku.cherryblossomgrotto.common.items.cherry_blossom;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class CherryBlossomChestBoat extends AbstractChestBoatItem {
    public CherryBlossomChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.CHERRY);
    }
}
