package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.deku.cherryblossomgrotto.common.utils.Randomizer;
import com.deku.cherryblossomgrotto.common.utils.RaytraceUtils;
import com.deku.cherryblossomgrotto.common.utils.BlockPosUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.List;
import java.util.function.BiConsumer;

import static com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.ModTrunkPlacerTypes.GRAND_CHERRY_TREE_TRUNK_PLACER;

public class GrandCherryBlossomTrunkPlacer extends GiantTrunkPlacer {
    public static final Codec<GrandCherryBlossomTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
        trunkPlacerParts(instance).apply(instance, GrandCherryBlossomTrunkPlacer::new)
    );

    public GrandCherryBlossomTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }

    /**
     * Fetches the associated trunk placer type for this trunk placer
     *
     * @return The trunk placer type
     */
    protected TrunkPlacerType<?> type() {
        return GRAND_CHERRY_TREE_TRUNK_PLACER.get();
    }

    /**
     * Places the trunk for this tree into the world.
     * This generates the primary trunk and any tertiary branches.
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random               A random number generator
     * @param trunkLength          The generalized length that the trunk will be
     * @param startingPosition     Position of the starting block in the world
     * @param treeConfig           Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkLength, BlockPos startingPosition, TreeConfiguration treeConfig) {
        setDirtAtBase(levelReader, blockConsumer, random, startingPosition, treeConfig);

        List<FoliagePlacer.FoliageAttachment> foliageList = Lists.newArrayList();

        foliageList.addAll(generateTrunk(levelReader, blockConsumer, random, trunkLength, startingPosition, treeConfig));
        foliageList.addAll(generateTertiaryBranches(levelReader, blockConsumer, random, foliageList, treeConfig));

        return foliageList;
    }

    /**
     * Sets dirt blocks below the spawning points of the tree's trunk
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param startingPosition Position of the starting block in the world
     */
    private void setDirtAtBase(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, BlockPos startingPosition, TreeConfiguration treeConfig) {
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below(), treeConfig);
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below().east(), treeConfig);
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below().south(), treeConfig);
        setDirtAt(levelReader, blockConsumer, random, startingPosition.below().south().east(), treeConfig);
    }

    /**
     * Generates the primary, straight trunk of the tree ascending on the Y axis
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    private List<FoliagePlacer.FoliageAttachment> generateTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkLength, BlockPos startingPosition, TreeConfiguration treeConfig) {
        List<FoliagePlacer.FoliageAttachment> trunkEnds = Lists.newArrayList();
        BlockPos.MutableBlockPos mutableBlockPosition = new BlockPos.MutableBlockPos();
        FoliagePlacer.FoliageAttachment foliageSpawn;

        for(int i = 0; i < trunkLength; ++i) {
            foliageSpawn = placeTrunkLog(levelReader, blockConsumer, random, trunkLength, startingPosition, treeConfig, mutableBlockPosition, 0, i, 0);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(levelReader, blockConsumer, random, trunkLength, startingPosition, treeConfig, mutableBlockPosition, 1, i, 0);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(levelReader, blockConsumer, random, trunkLength, startingPosition, treeConfig, mutableBlockPosition, 1, i, 1);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(levelReader, blockConsumer, random, trunkLength, startingPosition, treeConfig, mutableBlockPosition, 0, i, 1);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
        }

        return trunkEnds;
    }

    /**
     * Places a log at a specified position for generating a trunk
     * Generates a foliage placer if this is the supposed top of the trunk
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param mutableBlockPosition Mutable position for each block we want to place
     * @param offsetX The offset on the X axis from the starting position  that the log should spawn
     * @param offsetY The offset on the Y axis from the starting position that the log should spawn
     * @param offsetZ The offset on the Z axis from the starting position that the log should spawn
     * @return The possible foliage placer position, assuming that this last placed block will be the top of the trunk, or null
     */
    private FoliagePlacer.FoliageAttachment placeTrunkLog(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, int trunkLength, BlockPos startingPosition, TreeConfiguration treeConfig, BlockPos.MutableBlockPos mutableBlockPosition, int offsetX, int offsetY, int offsetZ) {
        mutableBlockPosition.setWithOffset(startingPosition, offsetX, offsetY, offsetZ);
        placeLogIfFree(levelReader, blockConsumer, random, mutableBlockPosition, treeConfig);
        if (offsetY == trunkLength - 1) {
            return new FoliagePlacer.FoliageAttachment(mutableBlockPosition.immutable(), 0, true);
        }
        return null;
    }

    /**
     * Generates all the tertiary branches that will form the rest of the tree's trunk.
     * Each branch is generated with a different axis in mind.
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param foliageList List of all currently-known possible foliage spawning points
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return All foliage spawning points along the tertiary branches
     */
    private List<FoliagePlacer.FoliageAttachment> generateTertiaryBranches(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, List<FoliagePlacer.FoliageAttachment> foliageList, TreeConfiguration treeConfig) {
        foliageList.addAll(generateGreedierTertiaryBranch(levelReader, blockConsumer, random, treeConfig, foliageList.get(0).pos(), Direction.EAST, Direction.NORTH));
        foliageList.addAll(generateGreedierTertiaryBranch(levelReader, blockConsumer, random, treeConfig, foliageList.get(1).pos(), Direction.WEST, Direction.NORTH));
        foliageList.addAll(generateGreedierTertiaryBranch(levelReader, blockConsumer, random, treeConfig, foliageList.get(2).pos(), Direction.WEST, Direction.SOUTH));
        foliageList.addAll(generateGreedierTertiaryBranch(levelReader, blockConsumer, random, treeConfig, foliageList.get(3).pos(), Direction.EAST, Direction.SOUTH));
        return foliageList;
    }

    /**
     * Generates a branch by placing logs at all positions intersected in a raytrace.
     * This is the less greediest form of branch generation.
     * It was adapted from the attempts in 1.14 of the mod.
     * It steps through blocks along the greatest distance in the predetermined path and for each possible point it gets a position along the path's direction
     * Leads to complete but somewhat thin branch.
     * NOTE: If needed, an even thinner branch could be generated using a clear-cut version of Bresenham's algorithm in 3D
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.FoliageAttachment> generateTertiaryBranch(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
        int branchStartOffset = Randomizer.getRandomNumberWithinBounds(random, 0, 2);
        int branchLengthX = Randomizer.getRandomNumberWithinBounds(random, 6, 8);
        int branchLengthY = Randomizer.getRandomNumberWithinBounds(random, 2, 3);
        int branchLengthZ = Randomizer.getRandomNumberWithinBounds(random, 6, 8);
        if (directionX == Direction.EAST) {
            branchLengthX *= -1;
        }
        if (directionZ == Direction.SOUTH) {
            branchLengthZ *= -1;
        }
        BlockPos trueStartingPosition = new BlockPos(startingPosition.getX(), startingPosition.getY() - branchStartOffset, startingPosition.getZ());
        BlockPos end = trueStartingPosition.offset(branchLengthX, branchLengthY, branchLengthZ);

        BlockPos distanceVector = end.subtract(trueStartingPosition);
        int longestDistance;
        int distanceX = Mth.abs(distanceVector.getX());
        int distanceY = Mth.abs(distanceVector.getY());
        int distanceZ = Mth.abs(distanceVector.getZ());
        if (distanceZ > distanceX && distanceZ > distanceY) {
            longestDistance = distanceZ;
        } else {
            longestDistance = Math.max(distanceY, distanceX);
        }

        BlockPos.MutableBlockPos mutableBlockPosition = new BlockPos.MutableBlockPos();
        Vector3d distanceVector2 = BlockPosUtils.normalizeToDirectionVector(distanceVector);
        int finalOffsetX = 0;
        int finalOffsetY = 0;
        int finalOffsetZ = 0;
        for(int layer = 0; layer < longestDistance; layer++) {
            mutableBlockPosition.setWithOffset(trueStartingPosition, (int) (layer * distanceVector2.x), (int) (layer * distanceVector2.y), (int) (layer * distanceVector2.z));
            placeLogIfFree(levelReader, blockConsumer, random, mutableBlockPosition, treeConfig);
            finalOffsetX = (int) (layer * distanceVector2.x);
            finalOffsetY = (int) (layer * distanceVector2.y);
            finalOffsetZ = (int) (layer * distanceVector2.z);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(trueStartingPosition.offset(finalOffsetX, finalOffsetY, finalOffsetZ), 0, true));
    }

    /**
     * Generates a branch by placing logs at all positions intersected in a raytrace.
     * This is a greedier (but not the greediest form) of branch generation.
     * It steps through block between the starting and ending positions of the branch and checks along a set number of steps via linear interpolation to see if a block should be placed or not.
     * By default the steps are done at x3 the number of blocks along the total route, so that we get a greedier but not too verbose branch forming
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.FoliageAttachment> generateGreedierTertiaryBranch(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
        List<BlockPos> greedyPoints = Lists.newArrayList();

        int branchStartOffset = Randomizer.getRandomNumberWithinBounds(random, 0, 2);
        int branchXOffset = Randomizer.getRandomNumberWithinBounds(random, 0, 1);
        int branchZOffset = Randomizer.getRandomNumberWithinBounds(random, 0, 1);

        int branchLengthX = Randomizer.getRandomNumberWithinBounds(random, 3, 4);
        int branchLengthY = Randomizer.getRandomNumberWithinBounds(random, 1, 2);
        int branchLengthZ = Randomizer.getRandomNumberWithinBounds(random, 3, 4);

        // Something to ensure that exact 45 degrees on a horizontal plane don't occur cause I dont like how it looks for the tree
        if (branchLengthX == branchLengthZ) {
            int branchXZPlaneOffset = Randomizer.getRandomNumberWithinBounds(random, 0, 1);
            int branchXZPlaneSign = Randomizer.getRandomNumberWithinBounds(random, 0, 1);
            if (branchXZPlaneSign < 1) {
                branchXZPlaneSign = -1;
            }
            if (branchXZPlaneOffset < 1) {
                branchLengthZ += branchXZPlaneSign;
            } else {
                branchLengthX += branchXZPlaneSign;
            }
        }

        if (directionX == Direction.EAST) {
            branchLengthX *= -1;
            branchXOffset += -1;
        }
        if (directionZ == Direction.SOUTH) {
            branchLengthZ *= -1;
            branchZOffset *= -1;
        }

        BlockPos trueStartingPosition = new BlockPos(startingPosition.getX() + branchXOffset, startingPosition.getY() - branchStartOffset, startingPosition.getZ() + branchZOffset);
        BlockPos end = trueStartingPosition.offset(branchLengthX, branchLengthY, branchLengthZ);

        BlockPos distanceVector = end.subtract(trueStartingPosition);
        double maxLength = BlockPosUtils.getMaxUnitOfAVector(distanceVector);

        // Note: Multiplying by three to increase the steps and cause more overlapping. I kinda like how it looks.
        maxLength *= 3;
        for (int i = 0; i <= maxLength; i++) {
            double current = maxLength == 0.0D ? 0.0D : i / maxLength;

            double interpolatedPositionX = Mth.lerp(current, trueStartingPosition.getX(), end.getX());
            double interpolatedPositionY = Mth.lerp(current, trueStartingPosition.getY(), end.getY());
            double interpolatedPositionZ = Mth.lerp(current, trueStartingPosition.getZ(), end.getZ());
            BlockPos nextPosition = new BlockPos(Math.round(interpolatedPositionX), Math.round(interpolatedPositionY), Math.round(interpolatedPositionZ));
            greedyPoints.add(nextPosition);
        }

        placeLogsAtGivenPositions(levelReader, blockConsumer, random, treeConfig, trueStartingPosition, greedyPoints);

        BlockPos middleOffset = greedyPoints.get(greedyPoints.size() / 2).subtract(trueStartingPosition);
        BlockPos finalOffset = greedyPoints.get(greedyPoints.size() - 1).subtract(trueStartingPosition);
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(trueStartingPosition.offset(finalOffset.getX(), finalOffset.getY(), finalOffset.getZ()), 0, false), new FoliagePlacer.FoliageAttachment(trueStartingPosition.offset(middleOffset.getX(), middleOffset.getY(), middleOffset.getZ()), 0, false));
    }

    /**
     * Generates a branch by placing logs at all positions intersected in a raytrace.
     * This is the greediest form of branch generation and does not skip a single block in the generation path
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.FoliageAttachment> generateGreediestTertiaryBranch(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
        int branchLengthX = Randomizer.getRandomNumberWithinBounds(random, 3, 4);
        int branchLengthY = Randomizer.getRandomNumberWithinBounds(random, 3, 5);
        int branchLengthZ = Randomizer.getRandomNumberWithinBounds(random, 3, 4);

        if (directionX == Direction.EAST) {
            branchLengthX *= -1;
        }
        if (directionZ == Direction.NORTH) {
            branchLengthZ *= -1;
        }
        BlockPos end = startingPosition.offset(branchLengthX, branchLengthY, branchLengthZ);
        List<BlockPos> greedyPoints = RaytraceUtils.traverseBlocks(new ClipContext(new Vec3(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ()), new Vec3(end.getX(), end.getY(), end.getZ()), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null), 1D);

        placeLogsAtGivenPositions(levelReader, blockConsumer, random, treeConfig, startingPosition, greedyPoints);

        BlockPos finalOffset = greedyPoints.get(greedyPoints.size() - 1).subtract(startingPosition);
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(startingPosition.offset(finalOffset.getX(), finalOffset.getY(), finalOffset.getZ()), 0, true));
    }

    /**
     * Places a list of logs at a given position in the world
     *
     * @param levelReader Reader of the level that this trunk is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param positionsToBePlaced The positions of all blocks that we want to place into the world
     */
    private void placeLogsAtGivenPositions(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, BlockPos startingPosition, List<BlockPos> positionsToBePlaced) {
        BlockPos.MutableBlockPos mutableBlockPosition = new BlockPos.MutableBlockPos();
        for(BlockPos branchPoint : positionsToBePlaced) {
            BlockPos currentOffset = branchPoint.subtract(startingPosition);
            mutableBlockPosition.setWithOffset(startingPosition, currentOffset.getX(), currentOffset.getY(), currentOffset.getZ());
            placeLogIfFree(levelReader, blockConsumer, random, mutableBlockPosition, treeConfig);
        }
    }
}
