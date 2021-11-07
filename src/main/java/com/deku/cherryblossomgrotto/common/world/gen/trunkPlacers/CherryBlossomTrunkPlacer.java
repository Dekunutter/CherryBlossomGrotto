package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.deku.cherryblossomgrotto.CherryBlossomGrotto.TrunkPlacerRegistryEventHandler.CHERRY_TREE_TRUNK_PLACER;

public class CherryBlossomTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<CherryBlossomTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, CherryBlossomTrunkPlacer::new)
    );

    private final int FOLIAGE_RADIUS_OFFSET = 1;
    private final int TRUNK_CURVE_OFFSET = 4;
    private final int TRUNK_CURVE_MAX_LENGTH = 3;
    private final boolean IS_THICK_TRUNK = false;

    private final int BRANCH_FOLIAGE_RADIUS_OFFSET = 0;
    private final int MINIMUM_BRANCHING_HEIGHT = 2;
    private final int BRANCH_START_HEIGHT_MAX_OFFSET = 4;
    private final int BRANCH_START_OFFSET = 2;
    private final int BRANCH_MAX_LENGTH = 3;

    public CherryBlossomTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }

    /**
     * Fetches the associated trunk placer type for this trunk placer
     *
     * @return The trunk placer type
     */
    @Override
    protected TrunkPlacerType<?> type() {
        return CHERRY_TREE_TRUNK_PLACER;
    }

    /**
     * Places the trunk for this tree into the world.
     * This generates the primary trunk and any tertiary branches.
     * Trunks rise vertically till a randomly determined point, upon which they start to skew horizontally at a randomly determined angle.
     * Starting points for foliage to spawn are added to the end of the trunk and all tertiary branches.
     * Note: Heavily based on how Acacia trees generate their trunks in the base game.
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
        setDirtAt(worldGenReader, startingPosition.below());

        List<FoliagePlacer.Foliage> foliageList = Lists.newArrayList();
        Direction trunkDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int trunkCurvingPoint = trunkLength - random.nextInt(TRUNK_CURVE_OFFSET) - 1;
        int curvingLength = TRUNK_CURVE_MAX_LENGTH - random.nextInt(TRUNK_CURVE_MAX_LENGTH);
        BlockPos.Mutable mutablePosition = new BlockPos.Mutable();
        int placementPositionX = startingPosition.getX();
        int placementPositionZ = startingPosition.getZ();
        int foliageStartY = 0;

        for (int currentTrunkPosition = 0; currentTrunkPosition < trunkLength; currentTrunkPosition++) {
            int placementPositionY = startingPosition.getY() + currentTrunkPosition;
            if (currentTrunkPosition >= trunkCurvingPoint && curvingLength > 0) {
                placementPositionX += trunkDirection.getStepX();
                placementPositionZ += trunkDirection.getStepZ();
                curvingLength--;
            }

            if (placeLog(worldGenReader, random, mutablePosition.set(placementPositionX, placementPositionY, placementPositionZ), placedBlockPositions, boundingBox, treeConfig)) {
                foliageStartY = placementPositionY + 1;
            }
        }

        foliageList.add(new FoliagePlacer.Foliage(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));

        generateBranch(worldGenReader, random, trunkLength, startingPosition, placedBlockPositions, trunkDirection, trunkCurvingPoint, boundingBox, treeConfig, foliageList);

        return foliageList;
    }

    /**
     * Places a branch for the trunk into the world.
     * The branch is generate around the position where the tree starts to curve.
     * If the direction of the branch matches the general direction of the tree, we'll skip on generating the branch.
     * If a branch is attempting to form too close to the ground, we will trace up the trunk till we are at a suitable distance above the ground
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param trunkLength The generalized length that the trunk will be
     * @param startingPosition Position of the starting block in the world
     * @param placedBlockPositions The position of all blocks placed into the world by this trunk generation
     * @param trunkDirection The direction that the trunk grows in
     * @param trunkCurvingPoint The relative point along the trunk in the Y-axis in which it begins to curve
     * @param boundingBox Bounding limitations of the generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param foliageList List of all foliage starting points
     */
    private void generateBranch(IWorldGenerationReader worldGenReader, Random random, int trunkLength, BlockPos startingPosition, Set<BlockPos> placedBlockPositions, Direction trunkDirection, int trunkCurvingPoint, MutableBoundingBox boundingBox, BaseTreeFeatureConfig treeConfig, List<FoliagePlacer.Foliage> foliageList) {
        int placementPositionX = startingPosition.getX();
        int placementPositionZ = startingPosition.getZ();
        Direction branchDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos.Mutable mutablePosition = new BlockPos.Mutable();
        int foliageStartY = 0;
        if (branchDirection != trunkDirection) {
            if (trunkLength - trunkCurvingPoint > BRANCH_START_HEIGHT_MAX_OFFSET) {
                return;
            }

            int branchingPoint = trunkCurvingPoint - random.nextInt(BRANCH_START_OFFSET) - 1;
            int branchLength = 1 + random.nextInt(BRANCH_MAX_LENGTH);

            for (int branchAttemptPoint = branchingPoint; branchAttemptPoint < trunkLength && branchLength > 0; branchLength--) {
                if (branchAttemptPoint >= MINIMUM_BRANCHING_HEIGHT) {
                    int branchingPositionY = startingPosition.getY() + branchAttemptPoint;
                    placementPositionX += branchDirection.getStepX();
                    placementPositionZ += branchDirection.getStepZ();

                    if (placeLog(worldGenReader, random, mutablePosition.set(placementPositionX, branchingPositionY, placementPositionZ), placedBlockPositions, boundingBox, treeConfig)) {
                        foliageStartY = branchingPositionY + 1;
                    }
                }

                branchAttemptPoint++;
            }

            if (foliageStartY > 1) {
                foliageList.add(new FoliagePlacer.Foliage(new BlockPos(placementPositionX, foliageStartY, placementPositionZ), BRANCH_FOLIAGE_RADIUS_OFFSET, IS_THICK_TRUNK));
            }
        }
    }
}
