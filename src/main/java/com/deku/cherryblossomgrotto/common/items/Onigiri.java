package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Onigiri extends Item {
    public Onigiri() {
        super(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(ModFoods.ONIGIRI));
        setRegistryName("onigiri");
    }
}
