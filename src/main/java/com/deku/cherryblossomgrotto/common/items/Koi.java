package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class Koi extends Item {
    public Koi() {
        super((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food(ModFoods.KOI));
        setRegistryName("koi");
    }
}
