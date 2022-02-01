package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.deku.cherryblossomgrotto.common.utils.Randomizer;
import com.deku.cherryblossomgrotto.common.utils.RaytraceUtils;
import com.deku.cherryblossomgrotto.common.utils.BlockPosUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.deku.cherryblossomgrotto.Main.TrunkPlacerRegistryEventHandler.GRAND_CHERRY_TREE_TRUNK_PLACER;

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
        return GRAND_CHERRY_TREE_TRUNK_PLACER;
    }

    /**
     * Places the trunk for this tree into the world.
     * This generates the primary trunk and any tertiary branches.
     *
     * @param worldGenReader       Instance of the world generator
     * @param random               A random number generator
     * @param trunkLength          The generalized length that the trunk will be
     * @param startingPosition     Position of the starting block in the world
     * @param placedBlockPositions The position of all blocks placed into the world by this trunk generation
     * @param boundingBox          Bounding limitations of the generator
     * @param treeConfig           Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    @Override
    public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos startingPosition, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig) {
        setDirtAtBase(worldGenReader, startingPosition);

        List<FoliagePlacer.Foliage> foliageList = Lists.newArrayList();

        foliageList.addAll(generateTrunk(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, boundingBox, treeConfig));
        foliageList.addAll(generateTertiaryBranches(worldGenReader, random, foliageList, placedBlockPositions, boundingBox, treeConfig));

        return foliageList;
    }

    /**
     * Sets dirt blocks below the spawning points of the tree's trunk
     *
     * @param worldGenReader Instance of the world generator
     * @param startingPosition Position of the starting block in the world
     */
    private void setDirtAtBase(IWorldGenerationReader worldGenReader, BlockPos startingPosition) {
        setDirtAt(worldGenReader, startingPosition.below());
        setDirtAt(worldGenReader, startingPosition.below().east());
        setDirtAt(worldGenReader, startingPosition.below().south());
        setDirtAt(worldGenReader, startingPosition.below().south().east());
    }

    /**
     * Generates the primary, straight trunk of the tree ascending on the Y axis
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return List of all points in the world where we will want to begin our foliage spawns
     */
    private List<FoliagePlacer.Foliage> generateTrunk(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos startingPosition, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig) {
        List<FoliagePlacer.Foliage> trunkEnds = Lists.newArrayList();
        BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
        FoliagePlacer.Foliage foliageSpawn;

        for(int i = 0; i < trunkLength; ++i) {
            foliageSpawn = placeTrunkLog(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, boundingBox, treeConfig, mutableBlockPosition, 0, i, 0);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, boundingBox, treeConfig, mutableBlockPosition, 1, i, 0);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, boundingBox, treeConfig, mutableBlockPosition, 1, i, 1);
            if (foliageSpawn != null) {
                trunkEnds.add(foliageSpawn);
            }
            foliageSpawn = placeTrunkLog(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, boundingBox, treeConfig, mutableBlockPosition, 0, i, 1);
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
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param mutableBlockPosition Mutable position for each block we want to place
     * @param offsetX The offset on the X axis from the starting position  that the log should spawn
     * @param offsetY The offset on the Y axis from the starting position that the log should spawn
     * @param offsetZ The offset on the Z axis from the starting position that the log should spawn
     * @return The possible foliage placer position, assuming that this last placed block will be the top of the trunk, or null
     */
    private FoliagePlacer.Foliage placeTrunkLog(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos startingPosition, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, BlockPos.Mutable mutableBlockPosition, int offsetX, int offsetY, int offsetZ) {
        mutableBlockPosition.setWithOffset(startingPosition, offsetX, offsetY, offsetZ);
        placeLogIfFree(worldGenReader, random, mutableBlockPosition, placedBlockPositions, boundingBox, treeConfig);
        if (offsetY == trunkLength - 1) {
            return new FoliagePlacer.Foliage(mutableBlockPosition.immutable(), 0, true);
        }
        return null;
    }

    /**
     * Generates all the tertiary branches that will form the rest of the tree's trunk.
     * Each branch is generated with a different axis in mind.
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param foliageList List of all currently-known possible foliage spawning points
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return All foliage spawning points along the tertiary branches
     */
    private List<FoliagePlacer.Foliage> generateTertiaryBranches(IWorldGenerationReader worldGenReader, Random random, List<FoliagePlacer.Foliage> foliageList, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig) {
        foliageList.addAll(generateGreedierTertiaryBranch(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, foliageList.get(0).foliagePos(), Direction.EAST, Direction.NORTH));
        foliageList.addAll(generateGreedierTertiaryBranch(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, foliageList.get(1).foliagePos(), Direction.WEST, Direction.NORTH));
        foliageList.addAll(generateGreedierTertiaryBranch(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, foliageList.get(2).foliagePos(), Direction.WEST, Direction.SOUTH));
        foliageList.addAll(generateGreedierTertiaryBranch(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, foliageList.get(3).foliagePos(), Direction.EAST, Direction.SOUTH));
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
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.Foliage> generateTertiaryBranch(IWorldGenerationReader worldGenReader, Random random, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
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
        int distanceX = MathHelper.abs(distanceVector.getX());
        int distanceY = MathHelper.abs(distanceVector.getY());
        int distanceZ = MathHelper.abs(distanceVector.getZ());
        if (distanceZ > distanceX && distanceZ > distanceY) {
            longestDistance = distanceZ;
        } else {
            longestDistance = Math.max(distanceY, distanceX);
        }

        BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
        Vector3d distanceVector2 = BlockPosUtils.normalizeToDirectionVector(distanceVector);
        int finalOffsetX = 0;
        int finalOffsetY = 0;
        int finalOffsetZ = 0;
        for(int layer = 0; layer < longestDistance; layer++) {
            mutableBlockPosition.setWithOffset(trueStartingPosition, (int) (layer * distanceVector2.x), (int) (layer * distanceVector2.y), (int) (layer * distanceVector2.z));
            placeLogIfFree(worldGenReader, random, mutableBlockPosition, placedBlockPositions, boundingBox, treeConfig);
            finalOffsetX = (int) (layer * distanceVector2.x);
            finalOffsetY = (int) (layer * distanceVector2.y);
            finalOffsetZ = (int) (layer * distanceVector2.z);
        }
        return ImmutableList.of(new FoliagePlacer.Foliage(trueStartingPosition.offset(finalOffsetX, finalOffsetY, finalOffsetZ), 0, true));
    }

    /**
     * Generates a branch by placing logs at all positions intersected in a raytrace.
     * This is a greedier (but not the greediest form) of branch generation.
     * It steps through block between the starting and ending positions of the branch and checks along a set number of steps via linear interpolation to see if a block should be placed or not.
     * By default the steps are done at x3 the number of blocks along the total route, so that we get a greedier but not too verbose branch forming
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.Foliage> generateGreedierTertiaryBranch(IWorldGenerationReader worldGenReader, Random random, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
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

            double interpolatedPositionX = MathHelper.lerp(current, trueStartingPosition.getX(), end.getX());
            double interpolatedPositionY = MathHelper.lerp(current, trueStartingPosition.getY(), end.getY());
            double interpolatedPositionZ = MathHelper.lerp(current, trueStartingPosition.getZ(), end.getZ());
            BlockPos nextPosition = new BlockPos(Math.round(interpolatedPositionX), Math.round(interpolatedPositionY), Math.round(interpolatedPositionZ));
            greedyPoints.add(nextPosition);
        }

        placeLogsAtGivenPositions(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, trueStartingPosition, greedyPoints);

        BlockPos middleOffset = greedyPoints.get(greedyPoints.size() / 2).subtract(trueStartingPosition);
        BlockPos finalOffset = greedyPoints.get(greedyPoints.size() - 1).subtract(trueStartingPosition);
        return ImmutableList.of(new FoliagePlacer.Foliage(trueStartingPosition.offset(finalOffset.getX(), finalOffset.getY(), finalOffset.getZ()), 0, false), new FoliagePlacer.Foliage(trueStartingPosition.offset(middleOffset.getX(), middleOffset.getY(), middleOffset.getZ()), 0, false));
    }

    /**
     * Generates a branch by placing logs at all positions intersected in a raytrace.
     * This is the greediest form of branch generation and does not skip a single block in the generation path
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param directionX The direction on the X axis in which this branch is being generated
     * @param directionZ The direction on the Z axis in which this branch is being generated
     * @return All foliage placer points generated along the branch
     */
    private List<FoliagePlacer.Foliage> generateGreediestTertiaryBranch(IWorldGenerationReader worldGenReader, Random random, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, BlockPos startingPosition, Direction directionX, Direction directionZ) {
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
        List<BlockPos> greedyPoints = RaytraceUtils.traverseBlocks(new RayTraceContext(new Vector3d(startingPosition.getX(), startingPosition.getY(), startingPosition.getZ()), new Vector3d(end.getX(), end.getY(), end.getZ()), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, null), 1D);

        placeLogsAtGivenPositions(worldGenReader, random, placedBlockPositions, boundingBox, treeConfig, startingPosition, greedyPoints);

        BlockPos finalOffset = greedyPoints.get(greedyPoints.size() - 1).subtract(startingPosition);
        return ImmutableList.of(new FoliagePlacer.Foliage(startingPosition.offset(finalOffset.getX(), finalOffset.getY(), finalOffset.getZ()), 0, true));
    }

    /**
     * Places a list of logs at a given position in the world
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param placedBlockPositions The position fo all blocks placed into the world by this trunk generation
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param startingPosition Position of the starting block in the world
     * @param positionsToBePlaced The positions of all blocks that we want to place into the world
     */
    private void placeLogsAtGivenPositions(IWorldGenerationReader worldGenReader, Random random, Set<BlockPos> placedBlockPositions, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, BlockPos startingPosition, List<BlockPos> positionsToBePlaced) {
        BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
        for(BlockPos branchPoint : positionsToBePlaced) {
            BlockPos currentOffset = branchPoint.subtract(startingPosition);
            mutableBlockPosition.setWithOffset(startingPosition, currentOffset.getX(), currentOffset.getY(), currentOffset.getZ());
            placeLogIfFree(worldGenReader, random, mutableBlockPosition, placedBlockPositions, boundingBox, treeConfig);
        }
    }
}
