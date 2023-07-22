package com.deku.cherryblossomgrotto.common.world.level.block.grower;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class SaxaulTreeGrower extends AbstractTreeGrower {

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasBees) {
        if (random.nextInt(10) < 2) {
            return ModTreeFeatures.LARGE_SAXAUL;
        } else {
            return ModTreeFeatures.SAXAUL;
        }
    }
}