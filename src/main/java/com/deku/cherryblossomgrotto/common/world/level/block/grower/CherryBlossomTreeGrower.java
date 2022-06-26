package com.deku.cherryblossomgrotto.common.world.level.block.grower;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
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
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasBees) {
        if (random.nextInt(10) < 5) {
            return hasBees ? Holder.direct(ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05) : Holder.direct(ModTreeFeatures.FANCY_CHERRY_BLOSSOM);
        } else {
            return hasBees ? Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM_BEES_05) : Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM);
        }
    }

    /**
     * Gets the configured mega feature that is spawned from this grower
     *
     * @param random Random number generator
     * @return A holder containing the configured mega feature spawned by this grower
     */
    @Nullable
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return Holder.direct(ModTreeFeatures.GRAND_CHERRY_BLOSSOM);
    }
}