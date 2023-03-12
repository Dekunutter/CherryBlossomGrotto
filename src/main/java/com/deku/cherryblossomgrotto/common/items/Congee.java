package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;

public class Congee extends BowlFoodItem {
    public Congee() {
        super(new Item.Properties().stacksTo(1).food(ModFoods.CONGEE));
    }
}
