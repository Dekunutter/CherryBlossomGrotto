package com.deku.cherryblossomgrotto.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CherryBlossomPetal extends Item {
    public CherryBlossomPetal() {
        super(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_MATERIALS));
        setRegistryName("cherry_blossom_petal");
    }
}
