package com.deku.eastwardjourneys.common.blockEntities;

import com.deku.eastwardjourneys.common.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MapleLeavesBlockEntity extends ParticleLeavesBlockEntity {
    public MapleLeavesBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntityType.MAPLE_LEAVES_TILE_DATA, position, state, ModBlocks.MAPLE_LEAF_PILE);
    }
}
