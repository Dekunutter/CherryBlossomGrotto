package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;


public class CherryBlossomTreeSpawner extends BigTree {
    /***
     * Determines the tree feature that this class will grow.
     * Has a chance to spawn the tree with a beehivce if bees are enabled by the spawner.
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @param hasBees Whether the tree can support bees
     * @return The tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasBees) {
        return hasBees ? ModFeatures.CHERRY_TREE_BEES_005 : ModFeatures.CHERRY_TREE;
    }

    /**
     * Determines the extra large tree feature that this class will grow
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @return The extra large tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random random) {
        return ModFeatures.FANCY_CHERRY_TREE;

    }
}