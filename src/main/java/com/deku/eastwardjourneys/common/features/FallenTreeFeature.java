package com.deku.eastwardjourneys.common.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class FallenTreeFeature extends Feature<FallenTreeFeature.Configuration> {
    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.MAGMA_BLOCK, Blocks.CHEST, Blocks.SPAWNER, Blocks.FIRE);
    private static final int MINIMUM_LENGTH = 3;

    public FallenTreeFeature(Codec<FallenTreeFeature.Configuration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> featureConfig) {
        BlockPos position = featureConfig.origin();
        WorldGenLevel level = featureConfig.level();
        RandomSource random = featureConfig.random();

        FallenTreeFeature.Configuration configuration = featureConfig.config();
        BlockState logToPlace = configuration.log().getState(random, position);
        int length = configuration.length().sample(random);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        List<BlockPos> validPositions = getPossiblePlacements(level, position.mutable(), length, direction);
        if (!canPlaceAt(level, position.mutable()) || validPositions.size() < MINIMUM_LENGTH) {
            return false;
        } else {
            logToPlace = logToPlace.setValue(RotatedPillarBlock.AXIS, direction.getAxis());

            // TODO: Add some leaves around the top of the tree to simulate the canopy on the fallen tree? May not be worth all the effort though unless I can somehow re-use the foliage placer logic since it would be unique for each tree type
            for(BlockPos placementPosition : validPositions) {
                level.setBlock(placementPosition, logToPlace, 2);
            }
        }

        return true;
    }

    private static boolean canPlaceAt(LevelAccessor level, BlockPos.MutableBlockPos position) {
        return canPlaceAt(level, position, false);
    }

    private static boolean canPlaceAt(LevelAccessor level, BlockPos.MutableBlockPos position, boolean checkForGround) {
        BlockState state = level.getBlockState(position);
        if ((state.isAir() || state.canBeReplaced()) && position.getY() > level.getMinBuildHeight()) {
            BlockState belowState = level.getBlockState(position.move(Direction.DOWN));

            boolean isValid;
            if (checkForGround) {
                boolean isSturdy = belowState.isFaceSturdy(level, position, Direction.UP);
                isValid = isSturdy && !CANNOT_PLACE_ON.contains(belowState.getBlock());

            } else {
                isValid = !CANNOT_PLACE_ON.contains(belowState.getBlock());
            }

            position.move(Direction.UP);
            return isValid;
        }
        return false;
    }

    private static List<BlockPos> getPossiblePlacements(LevelAccessor level, BlockPos.MutableBlockPos position, int length, Direction direction) {
        List<BlockPos> validPositions = new ArrayList<BlockPos>();
        int positiveLength = 0;
        for(int i = 0; i < length / 2; i++) {
            if(canPlaceAt(level, position)) {
                positiveLength++;
                validPositions.add(position.immutable());
                position.move(direction);
            } else {
                break;
            }
        }
        if (positiveLength > 0) {
            position.move(direction.getOpposite(), positiveLength);
        }

        int negativeLength = 0;
        for(int i = length / 2; i > 0; i--) {
            if(canPlaceAt(level, position)) {
                negativeLength++;
                validPositions.add(position.immutable());
                position.move(direction.getOpposite());
            } else {
                break;
            }
        }

        if (negativeLength > 0) {
            position.move(direction, negativeLength);
        }

        // Check to see if the true middle point is grounded, I dont want to place it if not so it doesnt spawn in weird floaty positions
        int center = (positiveLength - negativeLength) / 2;
        position.move(direction, center);
        if (!canPlaceAt(level, position, true)) {
            return new ArrayList<>();
        }
        position.move(direction.getOpposite(), center);

        return validPositions;
    }

    public static record Configuration(BlockStateProvider log, IntProvider length) implements FeatureConfiguration {
        public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((codec) -> {
            return codec.group(
                BlockStateProvider.CODEC.fieldOf("log").forGetter(FallenTreeFeature.Configuration::log),
                IntProvider.codec(3, 30).fieldOf("length").forGetter((v) -> { return v.length; })
            ).apply(codec, FallenTreeFeature.Configuration::new);
        });
    }
}
