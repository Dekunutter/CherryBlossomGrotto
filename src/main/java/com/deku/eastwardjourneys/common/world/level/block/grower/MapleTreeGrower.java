package com.deku.eastwardjourneys.common.world.level.block.grower;

import com.deku.eastwardjourneys.common.features.ModTreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;


public class MapleTreeGrower extends AbstractTreeGrower {

    /**
     * Gets the configured feature that is spawned from this grower.
     * Maple trees only spawn fancy versions, there are no basic trees for this breed.
     *
     * @param random Random number generator
     * @param hasBees Whether the grown feature has bees
     * @return A holder containing the configured feature spawned by this grower
     */
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasBees) {
        return hasBees ? ModTreeFeatures.FANCY_MAPLE_TREE_BEES : ModTreeFeatures.FANCY_MAPLE_TREE;
    }
}