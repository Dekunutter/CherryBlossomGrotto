package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Rice extends BlockNamedItem {
    public Rice() {
        super(ModBlocks.RICE_PADDY, new Item.Properties().tab(ItemGroup.TAB_FOOD).food(ModFoods.RICE));
        setRegistryName("rice");
    }
}
