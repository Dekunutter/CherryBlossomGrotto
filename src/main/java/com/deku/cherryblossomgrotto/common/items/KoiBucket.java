package com.deku.cherryblossomgrotto.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class KoiBucket extends FishBucketItem {
    public KoiBucket(EntityType<?> entityType) {
        super(entityType, Fluids.WATER, (new Item.Properties()).stacksTo(1).tab(ItemGroup.TAB_MISC));
        setRegistryName("koi_bucket");
    }
}
