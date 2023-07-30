package com.deku.eastwardjourneys.common.features;

import com.deku.eastwardjourneys.common.blocks.ModBlocks;
import com.deku.eastwardjourneys.common.blocks.RicePaddy;
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

public class RicePaddyFeature extends Feature<RicePaddyFeature.Configuration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public RicePaddyFeature(Codec<RicePaddyFeature.Configuration> codec) {
        super(codec);
    }

    /**
     * Attempts to place the given feature into the world.
     * Returns only when the feature has finished placing all its blocks.
     *
     * NOTE: A Copy of the vanilla lake feature. Just changes some of the generation patterns to suit our rice paddy.
     * Changes from vanilla:
     *   - When generating the barrier of the rice paddy, blocks below the water level can be magma
     *   - When generating the barrier off the rice paddy, blocks at the water level can be mossy cobblestone
     *   - The loop to check if some water should be replaced with ice is removed
     *
     * @param featureConfig The feature config for our rice paddies
     * @return Whether the feature was successfully placed or not
     */
    @Override
    public boolean place(FeaturePlaceContext<RicePaddyFeature.Configuration> featureConfig) {
        BlockPos position = featureConfig.origin();
        WorldGenLevel level = featureConfig.level();
        RandomSource random = featureConfig.random();
        RicePaddyFeature.Configuration config = featureConfig.config();

        // Returns if we are trying to spawn this rice paddy too close to bedrock
        if (position.getY() <= level.getMinBuildHeight() + 4) {
            return false;
        } else {
            // TODO: Can I change this generation to differ it from hotsprings a bit more. E.G: do we need to go 4 blocks deep, can we be more shallow, can I change the shape/size of the pool?
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
            BlockState grownRice = ModBlocks.RICE_PADDY.defaultBlockState().setValue(RicePaddy.AGE, RicePaddy.MAX_AGE);

            // Look through blocks in this chunk and see if a given block should be replaced by water?
            for (int chunkX = 0; chunkX < 16; ++chunkX) {
                for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                    for (int chunkY = 0; chunkY < 8; ++chunkY) {
                        boolean flag = !booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY] && (chunkX < 15 && booleanArray[((chunkX + 1) * 16 + chunkZ) * 8 + chunkY] || chunkX > 0 && booleanArray[((chunkX - 1) * 16 + chunkZ) * 8 + chunkY] || chunkZ < 15 && booleanArray[(chunkX * 16 + chunkZ + 1) * 8 + chunkY] || chunkZ > 0 && booleanArray[(chunkX * 16 + (chunkZ - 1)) * 8 + chunkY] || chunkY < 7 && booleanArray[(chunkX * 16 + chunkZ) * 8 + chunkY + 1] || chunkY > 0 && booleanArray[(chunkX * 16 + chunkZ) * 8 + (chunkY - 1)]);
                        if (flag) {
                            BlockState state = level.getBlockState(position.offset(chunkX, chunkY, chunkZ));
                            if (chunkY >= 4 && state.liquid()) {
                                return false;
                            }

                            if (chunkY < 4 && !state.isSolid() && level.getBlockState(position.offset(chunkX, chunkY, chunkZ)) != water) {
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
                                } else {
                                    // set block below to dirt
                                    if (random.nextInt(2) == 0 && this.canReplaceBlock(level.getBlockState(chunkPosition.below()))) {
                                        level.setBlock(chunkPosition.below(), Blocks.DIRT.defaultBlockState(), 2);
                                    }
                                    // set rice into water
                                    if (random.nextInt(3) == 0) {
                                        if (level.getBlockState(chunkPosition.below()) == Blocks.DIRT.defaultBlockState() && level.getBlockState(chunkPosition.above()) == Blocks.AIR.defaultBlockState()) {
                                            level.setBlock(chunkPosition, grownRice, 2);
                                        }
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
    public static record Configuration(BlockStateProvider fluid) implements FeatureConfiguration {
        public static final Codec<RicePaddyFeature.Configuration> CODEC = RecordCodecBuilder.create((codec) -> {
            return codec.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(RicePaddyFeature.Configuration::fluid)).apply(codec, RicePaddyFeature.Configuration::new);
        });
    }
}
