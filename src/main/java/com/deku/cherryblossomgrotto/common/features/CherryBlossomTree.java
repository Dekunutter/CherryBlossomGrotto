package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.CherryBlossomGrotto;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;


public class CherryBlossomTree extends BigTree {
    /***
     * Determines the tree feature that this class will grow
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @param hasBees Whether the tree can support bees
     * @return The tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasBees) {
        return CherryBlossomGrotto.WorldGenRegistryEventHandler.CHERRY_TREE;
    }

    /**
     * Determines the extra large tree feature that this class will grow
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @return The extra large tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random random) {
        return null;
    }
}