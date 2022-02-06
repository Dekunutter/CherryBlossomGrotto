package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.world.gen.topLayerModifications.ModTopLayerModifications;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CherryBlossomPetalCoverConfiguredFeature extends ConfiguredFeature<NoFeatureConfig, Feature<NoFeatureConfig>> {
    public CherryBlossomPetalCoverConfiguredFeature() {
        super(ModTopLayerModifications.CHERRY_BLOSSOM_GROUND_COVER.get(), IFeatureConfig.NONE);
    }
}
