package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreIronSparseConfiguredFeature extends ConfiguredFeature<OreFeatureConfig, Feature<OreFeatureConfig>> {
    public OreIronSparseConfiguredFeature() {
        super(Feature.ORE, (new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.defaultBlockState(), 9)));
        range(32);
        squared();
        count(2);
    }
}
