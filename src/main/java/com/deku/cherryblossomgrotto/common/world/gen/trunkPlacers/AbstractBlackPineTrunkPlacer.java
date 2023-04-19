package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class AbstractBlackPineTrunkPlacer extends TrunkPlacer {
    protected AbstractBlackPineTrunkPlacer.TrunkType trunkType = TrunkType.WOBBLY;

    public AbstractBlackPineTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }

    protected void setTrunkType(AbstractBlackPineTrunkPlacer.TrunkType type) {
        trunkType = type;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkLength, BlockPos startingPosition, TreeConfiguration treeConfig) {
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below(), treeConfig);

        List<FoliagePlacer.FoliageAttachment> foliageList = Lists.newArrayList();

        Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos.MutableBlockPos mutablePosition = new BlockPos.MutableBlockPos(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ());
        int placementPositionX = startingPosition.getX();
        int placementPositionZ = startingPosition.getZ();
        int foliageStartY = 0;

        if (trunkType == TrunkType.WOBBLY) {
            int segmentLength = 2;
            int segments = (int) Math.ceil((double) trunkLength / (double) segmentLength);
            boolean segmentFlip = false;

            for (int currentTrunkPosition = 0; currentTrunkPosition < trunkLength; currentTrunkPosition++) {
                int placementPositionY = startingPosition.getY() + currentTrunkPosition;
                BlockPos previousPosition = new BlockPos(mutablePosition);

                if (placementPositionY % segmentLength == 0) {
                    foliageList.add(new FoliagePlacer.FoliageAttachment(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), 1, false));

                    if (segmentFlip) {
                        placementPositionX += trunkDirection.getStepX();
                        placementPositionZ += trunkDirection.getStepZ();
                    } else {
                        placementPositionX -= trunkDirection.getStepX();
                        placementPositionZ -= trunkDirection.getStepZ();
                    }
                    segmentFlip = !segmentFlip;
                }

                mutablePosition.set(placementPositionX, placementPositionY, placementPositionZ);

                if (placeLog(levelReader, blockConsumer, random, mutablePosition, treeConfig, (blockState) -> blockState.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(mutablePosition, previousPosition)))) {
                    foliageStartY = placementPositionY + 1;
                }
            }

            foliageList.add(new FoliagePlacer.FoliageAttachment(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), 1, false));

            mutablePosition.set(placementPositionX, foliageStartY - 1, placementPositionZ);
            BlockPos.MutableBlockPos mutablePosition2 = new BlockPos.MutableBlockPos(placementPositionX, foliageStartY, placementPositionZ);
            placeLog(levelReader, blockConsumer, random, mutablePosition2, treeConfig, (blockState) -> blockState.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(mutablePosition, mutablePosition)));

            // place blocks at current X/Z
            // Increment/decrement in trunk direction and continue placing
        }

        return foliageList;
    }

    // NOTE: Could have abstract trunk placer instead of copying from AbstractCherryBlossomTrunkPlacer
    private Direction.Axis getLogAxis(BlockPos position, BlockPos previousPosition) {
        int xDiff = Math.abs(previousPosition.getX() - position.getX());
        int yDiff = Math.abs(previousPosition.getY() - position.getY());
        int zDiff = Math.abs(previousPosition.getZ() - position.getZ());

        if (xDiff > zDiff) {
            if (xDiff > yDiff) {
                return Direction.Axis.X;
            } else {
                return Direction.Axis.Y;
            }
        } else {
            if (zDiff > yDiff) {
                return Direction.Axis.Z;
            } else {
                return Direction.Axis.Y;
            }
        }
    }

    public enum TrunkType {
        WOBBLY,
        SPLIT,
        STRAIGHT,
        ANGLED
    }
}
