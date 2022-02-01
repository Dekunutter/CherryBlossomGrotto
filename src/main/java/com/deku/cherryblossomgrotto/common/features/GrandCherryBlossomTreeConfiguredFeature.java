package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.GrandCherryBlossomTrunkPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class GrandCherryBlossomTreeConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {
    public GrandCherryBlossomTreeConfiguredFeature() {
        super(Feature.TREE, (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new GrandCherryBlossomFoliagePlacer(FeatureSpread.fixed(5), FeatureSpread.fixed(0)),
                new GrandCherryBlossomTrunkPlacer(9, 2, 3),
                new TwoLayerFeature(1, 0, 2))
        ).ignoreVines().build());
    }
}
