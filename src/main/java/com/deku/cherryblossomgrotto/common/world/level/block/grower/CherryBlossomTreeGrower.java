package com.deku.cherryblossomgrotto.common.world.level.block.grower;

import com.deku.cherryblossomgrotto.common.features.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;


public class CherryBlossomTreeGrower extends AbstractTreeGrower {

    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean hasBees) {
        if (random.nextInt(10) < 5) {
            return hasBees ? ModConfiguredFeatures.FANCY_CHERRY_TREE_BEES_05 : ModConfiguredFeatures.FANCY_CHERRY_TREE;
        } else {
            return hasBees ? ModConfiguredFeatures.CHERRY_TREE_BEES_05 : ModConfiguredFeatures.CHERRY_TREE;
        }
    }

    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return ModConfiguredFeatures.GRAND_CHERRY_TREE;
    }
}