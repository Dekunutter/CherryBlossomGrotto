package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.FancyCherryBlossomTrunkPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;

import java.util.OptionalInt;

public class FancyCherryBlossomTreeConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {
    public FancyCherryBlossomTreeConfiguredFeature() {
        super(Feature.TREE, (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new FancyFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(2), 2),
                new FancyCherryBlossomTrunkPlacer(5, 3, 1),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines().build()));
    }
}
