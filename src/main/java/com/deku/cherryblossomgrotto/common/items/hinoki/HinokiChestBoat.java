package com.deku.cherryblossomgrotto.common.items.hinoki;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractChestBoatItem;

public class HinokiChestBoat extends AbstractChestBoatItem {
    public HinokiChestBoat() {
        super(new Properties().stacksTo(1), ModBoatTypes.HINOKI);
    }

}
