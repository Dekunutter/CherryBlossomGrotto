package com.deku.cherryblossomgrotto.common.items.maple;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class MapleChestBoat extends AbstractChestBoatItem {
    public MapleChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.MAPLE);
    }
}
