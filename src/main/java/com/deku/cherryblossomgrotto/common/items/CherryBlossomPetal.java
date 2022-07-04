package com.deku.cherryblossomgrotto.common.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class CherryBlossomPetal extends Item {
    public CherryBlossomPetal() {
        super(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MATERIALS));
    }
}
