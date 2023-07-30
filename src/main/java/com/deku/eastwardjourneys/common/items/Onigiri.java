package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.foods.ModFoods;
import net.minecraft.world.item.Item;

public class Onigiri extends Item {
    public Onigiri() {
        super(new Item.Properties().food(ModFoods.ONIGIRI));
    }
}
