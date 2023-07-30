package com.deku.eastwardjourneys.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public class AbstractWoodenSlabBlock extends SlabBlock implements IForgeBlock {
    public AbstractWoodenSlabBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * Sets the flammability of the block.
     * The higher the number the more likely it is to catch fire
     *
     * @param state State of the block being burned
     * @param blockGetter Getter for the block being burned
     * @param position Position of the block
     * @param face The face of the block being set on fire
     * @return The flammability value of the block given its position in the world and the face being set alight
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter blockGetter, BlockPos position, Direction face)
    {
        return 30;
    }

    /**
     * Determines how likely the fire is to destroy the block.
     * The higher the number the more likely it is to burn up.
     *
     * @param state State of the block being burned
     * @param blockGetter Getter for the block being burned
     * @param position Position of the block being burned
     * @param face The face of the block that's currently aflame
     * @return The likelihood that this burning block will be destroyed by the fire
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter blockGetter, BlockPos position, Direction face)
    {
        return 60;
    }
}
