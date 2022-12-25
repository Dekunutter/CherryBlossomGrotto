package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.world.item.Item;

public class CookedKoi extends Item {
    public CookedKoi() {
        super((new Item.Properties()).food(ModFoods.COOKED_KOI));
    }
}
