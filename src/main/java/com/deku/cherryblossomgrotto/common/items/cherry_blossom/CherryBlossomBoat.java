package com.deku.cherryblossomgrotto.common.items.cherry_blossom;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatTypes;
import com.deku.cherryblossomgrotto.common.items.AbstractBoatItem;
import net.minecraft.world.item.Item;

public class CherryBlossomBoat extends AbstractBoatItem {
    public CherryBlossomBoat() {
        super(new Item.Properties().stacksTo(1), ModBoatTypes.CHERRY);
    }
}
