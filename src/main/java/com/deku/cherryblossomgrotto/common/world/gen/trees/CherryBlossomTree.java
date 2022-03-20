package com.deku.cherryblossomgrotto.common.world.gen.trees;

import com.deku.cherryblossomgrotto.common.features.ModConfiguredFeatures;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;


public class CherryBlossomTree extends BigTree {
    /***
     * Determines the tree feature that this class will grow.
     * Has a chance to spawn the tree with a beehivce if bees are enabled by the spawner.
     * There is also a 50/50 chance that the tree will spawn as a fancy cherry blossom, which is a
     * slightly larger and more complex tree shape with different leaf canopy formation
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @param hasBees Whether the tree can support bees
     * @return The tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random random, boolean hasBees) {
        if (random.nextInt(10) < 5) {
            return hasBees ? ModConfiguredFeatures.FANCY_CHERRY_TREE_BEES_005 : ModConfiguredFeatures.FANCY_CHERRY_TREE;
        } else {
            return hasBees ? ModConfiguredFeatures.CHERRY_TREE_BEES_005 : ModConfiguredFeatures.CHERRY_TREE;
        }
    }

    /**
     * Determines the extra large tree feature that this class will grow
     *
     * @param random Randomizer for differentiating tree sub-types if needed
     * @return The extra large tree feature being grown by this class
     */
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random random) {
        return ModConfiguredFeatures.GRAND_CHERRY_TREE;
    }
}