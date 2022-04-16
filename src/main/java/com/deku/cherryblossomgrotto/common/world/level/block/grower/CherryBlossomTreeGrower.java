package com.deku.cherryblossomgrotto.common.world.level.block.grower;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;


public class CherryBlossomTreeGrower extends AbstractTreeGrower {

    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean hasBees) {
        if (random.nextInt(10) < 5) {
            return hasBees ? ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05 : ModTreeFeatures.FANCY_CHERRY_BLOSSOM;
        } else {
            return hasBees ? ModTreeFeatures.CHERRY_BLOSSOM_BEES_05 : ModTreeFeatures.CHERRY_BLOSSOM;
        }
    }

    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(Random random) {
        return ModTreeFeatures.GRAND_CHERRY_BLOSSOM;
    }
}