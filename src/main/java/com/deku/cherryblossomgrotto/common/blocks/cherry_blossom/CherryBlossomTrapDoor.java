package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomTrapDoor extends TrapDoorBlock {
    public CherryBlossomTrapDoor() {
        super(Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength( 3.0f).sound(SoundType.WOOD).noOcclusion().isValidSpawn(CherryBlossomTrapDoor::never), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN);
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
