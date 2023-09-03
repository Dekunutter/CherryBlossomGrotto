package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.foods.ModFoods;
import net.minecraft.world.item.Item;

public class Koi extends Item {
    public Koi() {
        super((new Item.Properties()).food(ModFoods.KOI));
    }
}
