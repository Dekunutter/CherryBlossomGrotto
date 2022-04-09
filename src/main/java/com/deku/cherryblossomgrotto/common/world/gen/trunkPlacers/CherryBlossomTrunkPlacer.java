package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.Random;

import static com.deku.cherryblossomgrotto.Main.TrunkPlacerRegistryEventHandler.CHERRY_TREE_TRUNK_PLACER;

public class CherryBlossomTrunkPlacer extends AbstractCherryBlossomTrunkPlacer {
    public static final Codec<CherryBlossomTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, CherryBlossomTrunkPlacer::new)
    );

    public CherryBlossomTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
        setBranchingType(BranchingType.SINGULAR);
        setTrunkCurvingOffset(4);
        setTrunkCurvingLengthMax(3);
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
     * Overrides of the branching direction validity check.
     * Instead this makes sure the branch isn't happening below the curving point.
     *
     * @param branchingDirection The direction that we want the branch to generate in
     * @param trunkDirection The direction that the trunk is generating in
     * @param trunkLength The generalized length of the trunk
     * @param trunkCurvingPoint The point along the trunk where we begin curving
     * @return Whether the direction that the branch will spawn in is valid
     */
    @Override
    protected boolean isBranchingDirectionValid(Direction branchingDirection, Direction trunkDirection, int trunkLength, int trunkCurvingPoint) {
        if (branchingDirection != trunkDirection) {
            return trunkLength - trunkCurvingPoint <= BRANCH_START_HEIGHT_MAX_OFFSET;
        }
        return false;
    }

    /**
     * Override the check to see if the branch is generating in an upward diagonal direction
     *
     * @param random A random number generator.
     * @return Returns true since all branches generate diagonally on this trunk type
     */
    @Override
    protected boolean isBranchDiagonal(Random random) {
        return true;
    }
}
