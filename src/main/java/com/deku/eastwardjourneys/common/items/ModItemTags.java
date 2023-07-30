package com.deku.eastwardjourneys.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModItemTags {
    public static final TagKey<Item> TANOOKI_TEMPTATIONS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "tanooki_tempt_items"));
    public static final TagKey<Item> TANOOKI_DESIREABLES = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "tanooki_desireables"));
}
