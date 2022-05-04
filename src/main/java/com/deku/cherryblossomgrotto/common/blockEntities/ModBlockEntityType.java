package com.deku.cherryblossomgrotto.common.blockEntities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModBlockEntityType {
    @ObjectHolder("cherry_leaves_tile_entity")
    public static BlockEntityType<CherryLeavesBlockEntity> CHERRY_LEAVES_TILE_DATA;
}
