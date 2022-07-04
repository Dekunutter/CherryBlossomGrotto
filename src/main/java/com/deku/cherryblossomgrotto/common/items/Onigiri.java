package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class Onigiri extends Item {
    public Onigiri() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.ONIGIRI));
    }
}
