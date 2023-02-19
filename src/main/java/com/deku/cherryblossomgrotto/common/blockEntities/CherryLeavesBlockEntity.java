package com.deku.cherryblossomgrotto.common.blockEntities;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CherryLeavesBlockEntity extends ParticleLeavesBlockEntity {
    public CherryLeavesBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntityType.CHERRY_LEAVES_TILE_DATA, position, state, ModBlocks.CHERRY_PETALS);
    }
}