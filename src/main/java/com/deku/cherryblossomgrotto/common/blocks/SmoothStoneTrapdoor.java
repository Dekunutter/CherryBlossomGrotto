package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;

public class SmoothStoneTrapdoor extends TrapDoorBlock {
    public SmoothStoneTrapdoor() {
        super(BlockBehaviour.Properties.of(Material.STONE, Blocks.SMOOTH_STONE.defaultMaterialColor()).strength(6.0f).noOcclusion().isValidSpawn(SmoothStoneTrapdoor::never), BlockSetType.STONE);
    }

    /**
     * A false predicate
     * Note: Should be able to just use an accesstransformer here instead of needing to re-code it locally but doesn't seem to be picking up my access modifier change when refreshing the project
     *
     * @param state State of the block
     * @param getter Getter for the block
     * @param position Position of the block
     * @param entityType Type of the entity
     * @return Boolean set to false
     */
    private static Boolean never(BlockState state, BlockGetter getter, BlockPos position, EntityType<?> entityType) {
        return false;
    }
}
