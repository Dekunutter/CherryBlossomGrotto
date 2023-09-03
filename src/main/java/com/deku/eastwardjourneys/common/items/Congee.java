
package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.foods.ModFoods;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;

public class Congee extends BowlFoodItem {
    public Congee() {
        super(new Item.Properties().stacksTo(1).food(ModFoods.CONGEE));
    }
}