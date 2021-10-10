package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CherryBlossomLog extends RotatedPillarBlock {

    public CherryBlossomLog() {
        super(AbstractBlock.Properties.of(Material.WOOD, (determineMaterialColour) ->
                determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_RED : MaterialColor.WOOD
            ).strength(2.0f).sound(SoundType.WOOD)
        );
        setRegistryName("cherry_blossom_log");
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 5;
    }
}
