package com.deku.eastwardjourneys.common.world.level.block.grower;

import com.deku.eastwardjourneys.common.features.ModTreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class BlackPineTreeGrower extends AbstractTreeGrower {

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasBees) {
        if (random.nextInt(10) < 4) {
            return ModTreeFeatures.BLACK_PINE;
        } else if (random.nextInt(10) < 7) {
            return ModTreeFeatures.STRAIGHT_BLACK_PINE;
        } else {
            return ModTreeFeatures.BRANCHING_BLACK_PINE;
        }
    }
}