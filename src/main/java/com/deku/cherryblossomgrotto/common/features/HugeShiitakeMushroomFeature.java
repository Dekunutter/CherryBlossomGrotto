package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.blocks.ShiitakeMushroom;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeShiitakeMushroomFeature extends AbstractHugeMushroomFeature {
    public HugeShiitakeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    protected BlockPos placeTrunk(LevelAccessor levelAccessor, RandomSource random, BlockPos position, HugeMushroomFeatureConfiguration configuration, int height, BlockPos.MutableBlockPos mutablePosition, BlockState originState) {
        BlockPos offsetPosition = placeTrunkBottom(levelAccessor, random, position, configuration, mutablePosition, originState);
        // Start at position 1 since position 0 is what we placed for the trunk bottom previously
        for(int i = 1; i < height; ++i) {
            mutablePosition.set(offsetPosition).move(Direction.UP, i);
            if (!levelAccessor.getBlockState(mutablePosition).isSolidRender(levelAccessor, mutablePosition)) {
                this.setBlock(levelAccessor, mutablePosition, configuration.stemProvider.getState(random, offsetPosition));
            }
        }

        return offsetPosition;
    }

    // Placing horizontal mushroom steams in whatever direction the shiitake grew from.
    private BlockPos placeTrunkBottom(LevelAccessor levelAccessor, RandomSource random, BlockPos position, HugeMushroomFeatureConfiguration configuration, BlockPos.MutableBlockPos mutablePosition, BlockState originState) {
        Direction direction = originState.getValue(ShiitakeMushroom.FACING);

        int length = random.nextInt(2) + 2;

        // Customizing blockstate so that the steam displays the internal texture on the correct side based on direction
        BlockState sidewaysState = Blocks.MUSHROOM_STEM.defaultBlockState();
        if (direction.getOpposite() == Direction.EAST) {
            sidewaysState.setValue(BlockStateProperties.EAST, false);
            sidewaysState.setValue(BlockStateProperties.WEST, true);
            sidewaysState.setValue(BlockStateProperties.SOUTH, true);
            sidewaysState.setValue(BlockStateProperties.NORTH, true);
            sidewaysState.setValue(BlockStateProperties.UP, true);
            sidewaysState.setValue(BlockStateProperties.DOWN, true);
        } else if (direction.getOpposite() == Direction.WEST) {
            sidewaysState.setValue(BlockStateProperties.EAST, true);
            sidewaysState.setValue(BlockStateProperties.WEST, false);
            sidewaysState.setValue(BlockStateProperties.SOUTH, true);
            sidewaysState.setValue(BlockStateProperties.NORTH, true);
            sidewaysState.setValue(BlockStateProperties.UP, true);
            sidewaysState.setValue(BlockStateProperties.DOWN, true);
        } else if (direction.getOpposite() == Direction.SOUTH) {
            sidewaysState.setValue(BlockStateProperties.EAST, true);
            sidewaysState.setValue(BlockStateProperties.WEST, true);
            sidewaysState.setValue(BlockStateProperties.SOUTH, false);
            sidewaysState.setValue(BlockStateProperties.NORTH, true);
            sidewaysState.setValue(BlockStateProperties.UP, true);
            sidewaysState.setValue(BlockStateProperties.DOWN, true);
        } else if (direction.getOpposite() == Direction.NORTH) {
            sidewaysState.setValue(BlockStateProperties.EAST, true);
            sidewaysState.setValue(BlockStateProperties.WEST, true);
            sidewaysState.setValue(BlockStateProperties.SOUTH, true);
            sidewaysState.setValue(BlockStateProperties.NORTH, false);
            sidewaysState.setValue(BlockStateProperties.UP, true);
            sidewaysState.setValue(BlockStateProperties.DOWN, true);
        }

        for (int i = 0; i < length; i++) {
            mutablePosition.set(position).move(direction, i);
            if (!levelAccessor.getBlockState(mutablePosition).isSolidRender(levelAccessor, mutablePosition)) {
                setBlock(levelAccessor, mutablePosition, sidewaysState);
            }
        }

        return mutablePosition.set(position).move(direction, length - 1).immutable();
    }

    @Override
    protected int getTreeRadiusForHeight(int min, int max, int foliageRadius, int height) {
        int i = 0;
        if (height < max && height >= max - 3) {
            i = foliageRadius;
        } else if (height == max) {
            i = foliageRadius;
        }

        return i;
    }

    protected int getTreeHeight(RandomSource p_224922_) {
        int i = p_224922_.nextInt(3) + 6;
        if (p_224922_.nextInt(12) == 0) {
            i *= 2;
        }

        return i;
    }

    @Override
    protected boolean isValidPosition(LevelAccessor levelAccessor, BlockPos position, int height, BlockPos.MutableBlockPos mutablePosition, HugeMushroomFeatureConfiguration configuration) {
        int i = position.getY();
        if (i >= levelAccessor.getMinBuildHeight() + 1 && i + height + 1 < levelAccessor.getMaxBuildHeight()) {
            for(int j = 0; j <= height; ++j) {
                int k = this.getTreeRadiusForHeight(-1, -1, configuration.foliageRadius, j);

                for(int l = -k; l <= k; ++l) {
                    for(int i1 = -k; i1 <= k; ++i1) {
                        BlockState blockstate1 = levelAccessor.getBlockState(mutablePosition.setWithOffset(position, l, j, i1));
                        if (!blockstate1.isAir() && !blockstate1.is(BlockTags.LEAVES) && !blockstate1.is(ModBlocks.SHIITAKE_MUSHROOM)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    // TODO: Only overridden cause I'll need to reposition the cap depending on how the trunk wants to form as it curves
    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> p_159436_) {
        WorldGenLevel worldgenlevel = p_159436_.level();
        BlockPos blockpos = p_159436_.origin();
        RandomSource randomsource = p_159436_.random();
        HugeMushroomFeatureConfiguration hugemushroomfeatureconfiguration = p_159436_.config();
        int i = this.getTreeHeight(randomsource);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        if (!this.isValidPosition(worldgenlevel, blockpos, i, blockpos$mutableblockpos, hugemushroomfeatureconfiguration)) {
            return false;
        } else {
            BlockState originState = worldgenlevel.getBlockState(blockpos);

            BlockPos offsetPosition = this.placeTrunk(worldgenlevel, randomsource, blockpos, hugemushroomfeatureconfiguration, i, blockpos$mutableblockpos, originState);
            this.makeCap(worldgenlevel, randomsource, new BlockPos(offsetPosition.getX(), blockpos.getY(), offsetPosition.getZ()), i, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
            return true;
        }
    }

    @Override
    protected void makeCap(LevelAccessor levelAccessor, RandomSource random, BlockPos position, int height, BlockPos.MutableBlockPos mutablePosition, HugeMushroomFeatureConfiguration configuration) {
        for(int i = height - 3; i <= height; ++i) {
            int j = i < height ? configuration.foliageRadius : configuration.foliageRadius - 1;
            int k = configuration.foliageRadius - 2;

            for(int l = -j; l <= j; ++l) {
                for(int i1 = -j; i1 <= j; ++i1) {
                    boolean flag = l == -j;
                    boolean flag1 = l == j;
                    boolean flag2 = i1 == -j;
                    boolean flag3 = i1 == j;
                    boolean flag4 = flag || flag1;
                    boolean flag5 = flag2 || flag3;
                    if (i >= height || flag4 != flag5) {
                        mutablePosition.setWithOffset(position, l, i, i1);
                        if (!levelAccessor.getBlockState(mutablePosition).isSolidRender(levelAccessor, mutablePosition)) {
                            BlockState blockstate = configuration.capProvider.getState(random, position);
                            if (blockstate.hasProperty(HugeMushroomBlock.WEST) && blockstate.hasProperty(HugeMushroomBlock.EAST) && blockstate.hasProperty(HugeMushroomBlock.NORTH) && blockstate.hasProperty(HugeMushroomBlock.SOUTH) && blockstate.hasProperty(HugeMushroomBlock.UP)) {
                                blockstate = blockstate.setValue(HugeMushroomBlock.UP, Boolean.valueOf(i >= height - 1)).setValue(HugeMushroomBlock.WEST, Boolean.valueOf(l < -k)).setValue(HugeMushroomBlock.EAST, Boolean.valueOf(l > k)).setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(i1 < -k)).setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(i1 > k));
                            }

                            this.setBlock(levelAccessor, mutablePosition, blockstate);
                        }
                    }
                }
            }
        }
    }
}
