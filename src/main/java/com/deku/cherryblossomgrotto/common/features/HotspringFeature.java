package com.deku.cherryblossomgrotto.common.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Material;

public class HotspringFeature extends Feature<HotspringFeature.Configuration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public HotspringFeature(Codec<HotspringFeature.Configuration> codec) {
        super(codec);
    }

    /**
     * Attempts to place the given feature into the world.
     * Returns only when the feature has finished placing all its blocks.
     *
     * NOTE: A Copy of the vanilla lake feature. Just changes some of the generation patterns to suit our hotsprings.
     * Changes from vanilla:
     *   - When generating the barrier of the hotspring, blocks below the water level can be magma
     *   - When generating the barrier off the hotspring, blocks at the water level can be mossy cobblestone
     *   - The loop to check if some water should be replaced with ice is removed
     *
     * @param featureConfig The feature config for our hotsprings
     * @return Whether the feature was successfully placed or not
     */
    @Override
    public boolean place(FeaturePlaceContext<HotspringFeature.Configuration> featureConfig) {
        BlockPos position = featureConfig.origin();
        WorldGenLevel level = featureConfig.level();
        RandomSource random = featureConfig.random();
        HotspringFeature.Configuration config = featureConfig.config();

        // Returns if we are trying to spawn this hotspring too close to bedrock
        if (position.getY() <= level.getMinBuildHeight() + 4) {
            return false;
        } else {
            // Get a position 4 blocks deep from the surface and an array of booleans which determines what blocks we replace in our generation
            position = position.below(4);
            boolean[] booleanArray = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for (int j = 0; j < i; ++j) {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int l = 1; l < 15; ++l) {
                    for (int i1 = 1; i1 < 15; ++i1) {
                        for (int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double) l - d3) / (d0 / 2.0D);
                            double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D) {
                                booleanArray[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState water = config.fluid().getState(random, position);

            // Look through blocks in this chunk and see if a given block should be replaced by water?
            for (int chunkX = 0; chunkX < 16; ++chunkX) {
                for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                    for (int chunkY = 0; chunkY < 8; ++chunkY) {
                        boolean flag = !booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY] && (chunkX < 15 && booleanArray[((chunkX + 1) * 16 + chunkZ) * 8 + chunkY] || chunkX > 0 && booleanArray[((chunkX - 1) * 16 + chunkZ) * 8 + chunkY] || chunkZ < 15 && booleanArray[(chunkX * 16 + chunkZ + 1) * 8 + chunkY] || chunkZ > 0 && booleanArray[(chunkX * 16 + (chunkZ - 1)) * 8 + chunkY] || chunkY < 7 && booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY + 1] || chunkY > 0 && booleanArray[(chunkX * 16 + chunkZ) * 8 + (chunkY - 1)]);
                        if (flag) {
                            Material material = level.getBlockState(position.offset(chunkX, chunkY, chunkZ)).getMaterial();
                            if (chunkY >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (chunkY < 4 && !material.isSolid() && level.getBlockState(position.offset(chunkX, chunkY, chunkZ)) != water) {
                                return false;
                            }
                        }
                    }
                }
            }

            // Set blocks above a certain level to air and those below to water?
            for (int chunkX = 0; chunkX < 16; ++chunkX) {
                for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                    for (int chunkY = 0; chunkY < 8; ++chunkY) {
                        if (booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY]) {
                            BlockPos chunkPosition = position.offset(chunkX, chunkY, chunkZ);
                            if (this.canReplaceBlock(level.getBlockState(chunkPosition))) {
                                boolean isAboveWaterLevel = chunkY >= 4;
                                level.setBlock(chunkPosition, isAboveWaterLevel ? AIR : water, 2);
                                if (isAboveWaterLevel) {
                                    level.scheduleTick(chunkPosition, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(level, chunkPosition);
                                }
                            }
                        }
                    }
                }
            }

            // Set blocks to stone that are currently solid and valid for replacement
            BlockState barrier = config.barrier().getState(random, position);
            if (!barrier.isAir()) {
                for (int chunkX = 0; chunkX < 16; ++chunkX) {
                    for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                        for (int chunkY = 0; chunkY < 8; ++chunkY) {
                            boolean flag2 = !booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY] && (chunkX < 15 && booleanArray[((chunkX + 1) * 16 + chunkZ) * 8 + chunkY] || chunkX > 0 && booleanArray[((chunkX - 1) * 16 + chunkZ) * 8 + chunkY] || chunkZ < 15 && booleanArray[(chunkX * 16 + chunkZ + 1) * 8 + chunkY] || chunkZ > 0 && booleanArray[(chunkX * 16 + (chunkZ - 1)) * 8 + chunkY] || chunkY < 7 && booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY + 1] || chunkY > 0 && booleanArray[(chunkX * 16 + chunkZ) * 8 + (chunkY - 1)]);
                            if (flag2) {
                                if ((chunkY < 4 || random.nextInt(2) != 0)) {
                                    BlockState chunkBlock = level.getBlockState(position.offset(chunkX, chunkY, chunkZ));
                                    if (chunkBlock.getMaterial().isSolid() && !chunkBlock.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                        BlockState blockToReplaceWith = barrier;
                                        if (chunkY < 3 && (random.nextInt(8) == 0)) {
                                            blockToReplaceWith = Blocks.MAGMA_BLOCK.defaultBlockState();
                                        } else if (chunkY == 3 && (random.nextInt(5) == 0)) {
                                            blockToReplaceWith = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
                                        }
                                        BlockPos chunkPosition = position.offset(chunkX, chunkY, chunkZ);
                                        level.setBlock(chunkPosition, blockToReplaceWith, 2);
                                        this.markAboveForPostProcessing(level, chunkPosition);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * Checks if the given block is replaceable by feature generation
     *
     * @param blockState State of the block being checked
     * @return Whether given block can be replaced
     */
    private boolean canReplaceBlock(BlockState blockState) {
        return !blockState.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    /**
     * The feature configuration for a hotspring.
     * Holds the block states for the valid fluid for this hotspring as well as the main block to be used for its barrier
     * @param fluid
     * @param barrier
     */
    public static record Configuration(BlockStateProvider fluid, BlockStateProvider barrier) implements FeatureConfiguration {
        public static final Codec<HotspringFeature.Configuration> CODEC = RecordCodecBuilder.create((codec) -> {
            return codec.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(HotspringFeature.Configuration::fluid), BlockStateProvider.CODEC.fieldOf("barrier").forGetter(HotspringFeature.Configuration::barrier)).apply(codec, HotspringFeature.Configuration::new);
        });
    }
}
