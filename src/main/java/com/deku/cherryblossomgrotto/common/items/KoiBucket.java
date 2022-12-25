package com.deku.cherryblossomgrotto.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;

public class KoiBucket extends MobBucketItem {
    public KoiBucket(EntityType<?> entityType) {
        super(entityType, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1));
    }
}
