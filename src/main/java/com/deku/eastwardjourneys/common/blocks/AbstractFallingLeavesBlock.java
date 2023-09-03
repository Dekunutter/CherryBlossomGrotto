package com.deku.eastwardjourneys.common.blocks;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class AbstractFallingLeavesBlock extends AbstractLeavesBlock implements EntityBlock {
    public AbstractFallingLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
