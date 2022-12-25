package com.deku.cherryblossomgrotto.common.world.level.block.grower;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;


public class CherryBlossomTreeGrower extends AbstractMegaTreeGrower {

    /**
     * Gets the configured feature that is spawned from this grower
     *
     * @param random Random number generator
     * @param hasBees Whether the grown feature has bees
     * @return A holder containing the configured feature spawned by this grower
     */
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasBees) {
        if (random.nextInt(10) < 5) {
            return hasBees ? ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05 : ModTreeFeatures.FANCY_CHERRY_BLOSSOM;
        } else {
            return hasBees ? ModTreeFeatures.CHERRY_BLOSSOM_BEES_05 : ModTreeFeatures.CHERRY_BLOSSOM;
        }
    }

    /**
     * Gets the configured mega feature that is spawned from this grower
     *
     * @param random Random number generator
     * @return A holder containing the configured mega feature spawned by this grower
     */
    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return ModTreeFeatures.GRAND_CHERRY_BLOSSOM;
    }
}