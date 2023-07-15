package com.deku.cherryblossomgrotto.common.items.hinoki;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractBoatItem;

public class HinokiBoat extends AbstractBoatItem {
    public HinokiBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.HINOKI);
    }
}
