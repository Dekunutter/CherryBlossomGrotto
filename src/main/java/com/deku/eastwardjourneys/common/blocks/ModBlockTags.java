package com.deku.eastwardjourneys.common.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModBlockTags {
    public static final TagKey<Block> MUSHROOM_GROW_BLOCK_WOOD = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "mushroom_grow_block_wood"));
}
