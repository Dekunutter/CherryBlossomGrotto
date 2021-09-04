package com.deku.cherryblossomgrotto.common.blocks.blockcolors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;

import javax.annotation.Nullable;

public class GrassBlockColor implements IBlockColor {
    public static final IBlockColor instance = new GrassBlockColor();

    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader world, @Nullable BlockPos position, int tintIndex) {
        if (world == null || position == null) {
            return GrassColors.get(0.5d, 1.0d);
        }
        return BiomeColors.getAverageGrassColor(world, position);
    }
}
