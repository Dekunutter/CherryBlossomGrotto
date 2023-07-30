package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.blocks.ModBlocks;
import com.deku.eastwardjourneys.common.foods.ModFoods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class Rice extends ItemNameBlockItem {
    public Rice() {
        super(ModBlocks.RICE_PADDY, new Item.Properties().food(ModFoods.RICE));
    }
}
