package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class TatamiMat extends CarpetBlock {
    public TatamiMat() {
        super(BlockBehaviour.Properties.of(Material.BAMBOO, MaterialColor.COLOR_BROWN).strength(0.2F).sound(SoundType.BAMBOO));
    }

    /**
     * Sets the flammability of the block. The higher the number the more likely it is to catch fire
     *
     * @param state State of the block
     * @param level Level that we are getting the block from
     * @param pos Position of the block
     * @param face The face of the block being set on fire
     * @return The flammability value of the block given its position in the world and the face being set alight
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face)
    {
        return 6;
    }

    /**
     * Determines how likely the fire is to destroy the block. The higher the number the more likely it is to burn up.
     *
     * @param state State of the block
     * @param level Level that the block exists in
     * @param pos Position of the block
     * @param face The face of the block that's currently aflame
     * @return The likelihood that this burning block will be destroyed by the fire
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction face)
    {
        return 6;
    }
}
