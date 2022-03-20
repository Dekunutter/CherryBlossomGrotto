package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CookedKoi extends Item {
    public CookedKoi() {
        super((new Item.Properties()).tab(ItemGroup.TAB_FOOD).food(ModFoods.COOKED_KOI));
        setRegistryName("cooked_koi");
    }
}
