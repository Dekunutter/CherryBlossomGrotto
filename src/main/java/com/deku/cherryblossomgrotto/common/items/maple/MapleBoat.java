package com.deku.cherryblossomgrotto.common.items.maple;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractBoatItem;

public class MapleBoat extends AbstractBoatItem {
    public MapleBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.MAPLE);
    }
}
