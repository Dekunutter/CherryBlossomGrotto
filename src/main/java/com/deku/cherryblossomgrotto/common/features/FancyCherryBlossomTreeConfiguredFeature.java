package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;

import java.util.OptionalInt;

public class FancyCherryBlossomTreeConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {
    public FancyCherryBlossomTreeConfiguredFeature() {
        super(Feature.TREE, (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2),
                new FancyTrunkPlacer(6, 8, 1),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines().build()));
    }
}
