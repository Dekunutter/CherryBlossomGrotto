package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class Rice extends ItemNameBlockItem {
    public Rice() {
        super(ModBlocks.RICE_PADDY, new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.RICE));
    }
}
