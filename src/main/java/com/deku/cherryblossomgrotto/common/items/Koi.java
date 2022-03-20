package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Koi extends Item {
    public Koi() {
        super((new Item.Properties()).tab(ItemGroup.TAB_FOOD).food(ModFoods.KOI));
        setRegistryName("koi");
    }
}
