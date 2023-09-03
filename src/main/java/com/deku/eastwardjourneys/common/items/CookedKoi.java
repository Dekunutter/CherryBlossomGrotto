package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.foods.ModFoods;
import net.minecraft.world.item.Item;

public class CookedKoi extends Item {
    public CookedKoi() {
        super((new Item.Properties()).food(ModFoods.COOKED_KOI));
    }
}
