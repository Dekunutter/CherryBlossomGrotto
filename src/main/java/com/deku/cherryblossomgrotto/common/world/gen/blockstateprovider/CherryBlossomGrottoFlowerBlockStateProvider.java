package com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class CherryBlossomGrottoFlowerBlockStateProvider extends BlockStateProvider {
    public static final Codec<CherryBlossomGrottoFlowerBlockStateProvider> CODEC;
    public static final BlockState[] FLOWERS = new BlockState[] {Blocks.LILAC.defaultBlockState(), Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), Blocks.ALLIUM.defaultBlockState(), Blocks.PEONY.defaultBlockState()};
    public static final CherryBlossomGrottoFlowerBlockStateProvider INSTANCE = new CherryBlossomGrottoFlowerBlockStateProvider();

    /**
     * Gets the block state provider type of the current block state provider
     *
     * @return The block state provider type for this block state provider
     */
    protected BlockStateProviderType<?> type() {
        return ModBlockStateProviderType.CHERRY_FOREST_FLOWERS;
    }

    /**
     * Determines which flower will spawn from this block state provider when its state is called.
     * Picks a flower from the array of block states in this provider at random
     *
     * @param random A random number generator
     * @param position The position that we are setting block state for
     * @return The block state returned by the provider
     */
    public BlockState getState(RandomSource random, BlockPos position) {
        double randomFlowerChoice = Mth.clamp((1.0d + Biome.BIOME_INFO_NOISE.getValue((double) position.getX() / 48.0d, (double) position.getZ() / 48.0d, false)) / 2.0d, 0.0d, 0.9999d);
        return FLOWERS[(int) (randomFlowerChoice * (double) FLOWERS.length)];
    }

    static {
        CODEC = Codec.unit(() -> {
            return INSTANCE;
        });
    }
}
