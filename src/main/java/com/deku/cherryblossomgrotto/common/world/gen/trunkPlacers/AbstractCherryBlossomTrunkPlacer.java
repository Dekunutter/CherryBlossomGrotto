package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public abstract class AbstractCherryBlossomTrunkPlacer extends TrunkPlacer {
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
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, Random random, int trunkLength, BlockPos startingPosition, TreeConfiguration treeConfig) {
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below(), treeConfig);

        List<FoliagePlacer.FoliageAttachment> foliageList = Lists.newArrayList();

        Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int trunkCurvingPoint = calculateTrunkCurvingPoint(random, trunkLength);
        int curvingLength = calculateTrunkCurvingLength(random);

        BlockPos.MutableBlockPos mutablePosition = new BlockPos.MutableBlockPos(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ());
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
            if (placeLog(levelReader, blockConsumer, random, mutablePosition, treeConfig, (blockState) -> blockState.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(mutablePosition, previousPosition)))) {
                foliageStartY = placementPositionY + 1;
            }

            // spawning branches on any fancy cherry blossom trees
            if (isCanopyBranchingCapable(random, currentTrunkPosition, trunkLength)) {
                generateBranch(levelReader, blockConsumer, random, trunkLength, mutablePosition.immutable(), trunkDirection, trunkCurvingPoint, treeConfig, foliageList);
            }
        }

        foliageList.add(new FoliagePlacer.FoliageAttachment(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));

        // spawn a singular branch on non-fancy cherry blossom tree spawns
        if (branchingType == BranchingType.SINGULAR) {
            generateBranch(levelReader, blockConsumer, random, trunkLength, mutablePosition.immutable(), trunkDirection, trunkCurvingPoint, treeConfig, foliageList);
        }

        return foliageList;
    }

    /**
     * Generates a single branch for the current tree.
     * Branches only generate if they are not going to be moving parallel to the direction of the trunk and are within the right height constraints.
     * Branches have a chance of spawning in a upward diagonal angle, but generally they will tend to be flat along the X/Z horizontal planes.
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param branchingPosition Position of the starting block for this branch. Should be a block on the trunk
     * @param trunkDirection The direction that the trunk has generated in
     * @param trunkCurvingPoint The point along the trunk that the curve starts
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param foliageList List of all foliage spawning points for this tree
     */
    protected void generateBranch(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, Random random, int trunkLength, BlockPos branchingPosition, Direction trunkDirection, int trunkCurvingPoint, TreeConfiguration treeConfig, List<FoliagePlacer.FoliageAttachment> foliageList) {
        int placementPositionX = branchingPosition.getX();
        int placementPositionY = branchingPosition.getY();
        int placementPositionZ = branchingPosition.getZ();

        Direction branchHorizontalDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction branchVerticalDirection = Direction.UP;

        if (!isBranchingDirectionValid(branchHorizontalDirection, trunkDirection, trunkLength, trunkCurvingPoint)) {
            return;
        }

        BlockPos.MutableBlockPos mutablePosition = new BlockPos.MutableBlockPos(branchingPosition.getX(), branchingPosition.getY(), branchingPosition.getZ());
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

                if (placeLog(levelReader, blockConsumer, random, mutablePosition, treeConfig, (blockState) -> blockState.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(mutablePosition, previousPosition)))) {
                    foliageStartY = placementPositionY + 1;
                }
            }
            currentBranchingPoint++;
        }

        if (foliageStartY > 1) {
            foliageList.add(new FoliagePlacer.FoliageAttachment(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), BRANCH_FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));
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
