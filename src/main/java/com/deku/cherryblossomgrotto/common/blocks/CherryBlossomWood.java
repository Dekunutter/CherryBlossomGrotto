package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CherryBlossomWood extends RotatedPillarBlock {
    public CherryBlossomWood() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(2.0f).sound(SoundType.WOOD));
        setRegistryName("cherry_blossom_wood");
    }

    /**
     * Sets the flammability of the block. The higher the number the more likely it is to catch fire
     *
     * @param state State of the block
     * @param world World that the block exists in
     * @param pos Position of the block
     * @param face The face of the block being set on fire
     * @return The flammability value of the block given its position in the world and the face being set alight
     */
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 5;
    }

    /**
     * Determines how likely the fire is to destroy the block. The higher the number the more likely it is to burn up.
     *
     * @param state State of the block
     * @param world World that the block exists in
     * @param pos Position of the block
     * @param face The face of the block that's currently aflame
     * @return The likelihood that this burning block will be destroyed by the fire
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 5;
    }
}
