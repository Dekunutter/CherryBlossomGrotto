package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.google.common.collect.Lists;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class AbstractCherryBlossomTrunkPlacer extends AbstractTrunkPlacer {
    protected final int FOLIAGE_RADIUS_OFFSET = 1;
    protected final int BRANCH_FOLIAGE_RADIUS_OFFSET = 0;

    protected final boolean IS_THICK_TRUNK = false;

    protected final int MINIMUM_BRANCHING_HEIGHT = 2;
    protected final int BRANCH_START_HEIGHT_MAX_OFFSET = 4;
    protected final int BRANCH_START_OFFSET = 2;
    protected final int BRANCH_MAX_LENGTH = 3;

    protected BranchingType branchingType = BranchingType.CANOPY;
    protected int trunkCurvingOffset = 4;
    protected int trunkCurvingLengthMax = 3;

    public AbstractCherryBlossomTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }

    /**
     * Sets the branching type on this trunk placer instance.
     *
     * @param type The branching type enum we want to set
     */
    protected void setBranchingType(BranchingType type) {
        branchingType = type;
    }

    /**
     * Sets the trunk curving offset on this trunk placer instance.
     *
     * @param offset The trunk curving offset we want to set
     */
    protected void setTrunkCurvingOffset(int offset) {
        trunkCurvingOffset = offset;
    }

    /**
     * Sets the maximum length of the trunk's curve on this instance
     *
     * @param length The maximum length of the trunk's curve
     */
    protected void setTrunkCurvingLengthMax(int length) {
        trunkCurvingLengthMax = length;
    }

    /**
     * Places the trunk for this tree into the world.
     * This generates the primary trunk and any tertiary branches.
     * Trunks rise vertically till a randomly determined point, upon which they start to skew horizontally at a randomly determined angle.
     * The way that any branches are spawned is determined via the setting of an enum value.
     * Starting points for foliage to spawn are added to the end of the trunk and all tertiary branches.
     * Note: This is a heavy adaption of how acacia trees generate their trunks in vanilla.
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param placedBlockPositions The position of all blocks placed into the world by this trunk generator
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    @Override
    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos startingPosition, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig) {
        if (!treeConfig.fromSapling) {
            setDirtAt(worldGenReader, startingPosition.below());
        }

        List<FoliagePlacer.Foliage> foliageList = Lists.newArrayList();

        Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int trunkCurvingPoint = calculateTrunkCurvingPoint(random, trunkLength);
        int curvingLength = calculateTrunkCurvingLength(random);

        BlockPos.Mutable mutablePosition = new BlockPos.Mutable(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ());
        int placementPositionX = startingPosition.getX();
        int placementPositionZ = startingPosition.getZ();
        int foliageStartY = 0;

        for (int currentTrunkPosition = 0; currentTrunkPosition < trunkLength; currentTrunkPosition++) {
            int placementPositionY = startingPosition.getY() + currentTrunkPosition;
            if (isPastTrunkCurvingPoint(currentTrunkPosition, trunkCurvingPoint, curvingLength)) {
                placementPositionX += trunkDirection.getStepX();
                placementPositionZ += trunkDirection.getStepZ();
                curvingLength--;
            }

            BlockPos previousPosition = new BlockPos(mutablePosition);
            mutablePosition.set(placementPositionX, placementPositionY, placementPositionZ);

            // place individual logs with appropriate rotation to build up the trunk
            if (placeLogWithRotation(worldGenReader, random, previousPosition, mutablePosition, placedBlockPositions, boundingBox, treeConfig)) {
                foliageStartY = placementPositionY + 1;
            }

            // spawning branches on any fancy cherry blossom trees
            if (isCanopyBranchingCapable(random, currentTrunkPosition, trunkLength)) {
                generateBranch(worldGenReader, random, trunkLength, mutablePosition.immutable(), placedBlockPositions, trunkDirection, trunkCurvingPoint, boundingBox, treeConfig, foliageList);
            }
        }

        foliageList.add(new FoliagePlacer.Foliage(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));

        // spawn a singular branch on non-fancy cherry blossom tree spawns
        if (branchingType == BranchingType.SINGULAR) {
            generateBranch(worldGenReader, random, trunkLength, mutablePosition.immutable(), placedBlockPositions, trunkDirection, trunkCurvingPoint, boundingBox, treeConfig, foliageList);
        }

        return foliageList;
    }

    /**
     * Generates a single branch for the current tree.
     * Branches only generate if they are not going to be moving parallel to the direction of the trunk and are within the right height constraints.
     * Branches have a chance of spawning in a upward diagonal angle, but generally they will tend to be flat along the X/Z horizontal planes.
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param branchingPosition Position of the starting block for this branch. Should be a block on the trunk
     * @param placedBlockPositions The position of all blocks placed into the world by this trunk generator
     * @param trunkDirection The direction that the trunk has generated in
     * @param trunkCurvingPoint The point along the trunk that the curve starts
     * @param boundingBox The bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param foliageList List of all foliage spawning points for this tree
     */
    protected void generateBranch(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos branchingPosition, Set<BlockPos> placedBlockPositions, Direction trunkDirection, int trunkCurvingPoint, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, List<FoliagePlacer.Foliage> foliageList) {
        int placementPositionX = branchingPosition.getX();
        int placementPositionY = branchingPosition.getY();
        int placementPositionZ = branchingPosition.getZ();

        Direction branchHorizontalDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction branchVerticalDirection = Direction.UP;

        if (!isBranchingDirectionValid(branchHorizontalDirection, trunkDirection, trunkLength, trunkCurvingPoint)) {
            return;
        }

        BlockPos.Mutable mutablePosition = new BlockPos.Mutable(branchingPosition.getX(), branchingPosition.getY(), branchingPosition.getZ());
        int foliageStartY = 0;

        int branchingPoint = calculateBranchingPoint(random, trunkCurvingPoint);
        int branchLength = calculateBranchLength(random);
        boolean isDiagonalBranch = isBranchDiagonal(random);
        for (int currentBranchingPoint = branchingPoint; currentBranchingPoint < trunkLength && branchLength > 0; branchLength--) {
            if (currentBranchingPoint >= MINIMUM_BRANCHING_HEIGHT) {
                placementPositionX += branchHorizontalDirection.getStepX();
                if (isDiagonalBranch) {
                    placementPositionY += branchVerticalDirection.getStepY();
                }
                placementPositionZ += branchHorizontalDirection.getStepZ();

                BlockPos previousPosition = new BlockPos(mutablePosition);
                mutablePosition.set(placementPositionX, placementPositionY, placementPositionZ);

                if (placeLogWithRotation(worldGenReader, random, previousPosition, mutablePosition, placedBlockPositions, boundingBox, treeConfig)) {
                    foliageStartY = placementPositionY + 1;
                }
            }
            currentBranchingPoint++;
        }

        if (foliageStartY > 1) {
            foliageList.add(new FoliagePlacer.Foliage(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), BRANCH_FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));
        }
    }

    /**
     * Places a log into the world with a specified rotation.
     * Logs will not be placed if it is deemed an invalid position for a tree feature to place a block.
     * This is an adaption of the placeLog function in the vanilla trunk placers.
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param previousPosition The position of the previously placed log in the sequence
     * @param currentPosition The position we want to place the current log
     * @param placedBlockPositions The position of all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return Whether the log was placed into the world at the current positions successfully
     */
    protected boolean placeLogWithRotation(IWorldGenerationReader worldGenReader, Random random, BlockPos previousPosition, BlockPos currentPosition, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig) {
        if (TreeFeature.validTreePos(worldGenReader, currentPosition)) {
            setBlock(worldGenReader, currentPosition, treeConfig.trunkProvider.getState(random, currentPosition).setValue(RotatedPillarBlock.AXIS, getLogAxis(currentPosition, previousPosition)), boundingBox);
            placedBlockPositions.add(currentPosition.immutable());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate the curving point for the trunk
     *
     * @param random A random number generator
     * @param trunkLength The generalized length of the trunk
     * @return The point along the trunk that the curve will begin
     */
    private int calculateTrunkCurvingPoint(Random random, int trunkLength) {
        return trunkLength - random.nextInt(trunkCurvingOffset) - 1;
    }

    /**
     * Calculate the curve length for the trunk
     *
     * @param random A random number generator
     * @return The length that the curve will generate for
     */
    private int calculateTrunkCurvingLength(Random random) {
        return trunkCurvingLengthMax - random.nextInt(trunkCurvingLengthMax);
    }

    /**
     * Checks to see if a given position is passed the curving point for this trunk
     *
     * @param currentPosition The position that we are checking against the curving point
     * @param trunkCurvingPoint The point along the trunk where the curve begins
     * @param remainingLength The remaining length in the current curve during generation
     * @return
     */
    private boolean isPastTrunkCurvingPoint(int currentPosition, int trunkCurvingPoint, int remainingLength) {
        return currentPosition >= trunkCurvingPoint && remainingLength > 0;
    }

    /**
     * Checks to see if the tree is capable of creating canopy branches.
     * Canopy branching only works if the canopy branching type is set and the current position is near, but not above, the top of the tree trunk
     *
     * @param random A random number generator
     * @param currentPosition The position that we are currently at in trunk generation
     * @param trunkLength The generalized length of the trunk
     * @return Whether the trunk is at a point where it is capable of generating branches near its canopy
     */
    private boolean isCanopyBranchingCapable(Random random, int currentPosition, int trunkLength) {
        if (branchingType == BranchingType.CANOPY && random.nextInt(10) >= 1) {
            return (currentPosition > 3) && (currentPosition <= trunkLength - 2);
        }
        return false;
    }

    /**
     * Checks to see if the desired branching direction is valid for branch generation.
     * Also does a high level check to ensure that the branch isnt trying to generate too high along the trunk, out of its bounds.
     *
     * @param branchingDirection The direction that we want the branch to generate in
     * @param trunkDirection The direction that the trunk is generating in
     * @param trunkLength The generalized length of the trunk
     * @param trunkCurvingPoint The point along the trunk where we begin curving
     * @return Whether the direction of the branch is valid for spawning.
     */
    protected boolean isBranchingDirectionValid(Direction branchingDirection, Direction trunkDirection, int trunkLength, int trunkCurvingPoint) {
        if (branchingDirection != trunkDirection) {
            return trunkLength > BRANCH_START_HEIGHT_MAX_OFFSET;
        }
        return false;
    }

    /**
     * Calculates the point at which a branch will begin generating.
     *
     * @param random A random number generator
     * @param trunkCurvingPoint The point along the trunk where the curve begins
     * @return The point along the trunk where a branch will begin.
     */
    private int calculateBranchingPoint(Random random, int trunkCurvingPoint) {
        return trunkCurvingPoint - random.nextInt(BRANCH_START_OFFSET) - 1;
    }

    /**
     * Calculates the length of a branch
     *
     * @param random A random number generator.
     * @return The length we want the branch to be
     */
    private int calculateBranchLength(Random random) {
        return 1 + random.nextInt(BRANCH_MAX_LENGTH);
    }

    /**
     * Checks if the branch is going to spawn in an upward diagonal direction
     *
     * @param random A random number generator.
     * @return Whether the branch is going to spawn in an upward diagonal direction
     */
    protected boolean isBranchDiagonal(Random random) {
        return random.nextInt(9) <= 1;
    }

    /**
     * The axis we want the log to spawn in.
     * This controls the rotation of the log when it is placed into the world.
     * Compares the position of the current and previous log positions to get the greatest axis of difference between them.
     *
     * @param position The position that the log will be spawning in
     * @param previousPosition The position of the previous log in the sequence
     * @return The axis we want the log to spawn in
     */
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

    /**
     * Enum class for determining the branching type of this trunk spawner
     */
    public enum BranchingType {
        SINGULAR,
        CANOPY
    }
}
