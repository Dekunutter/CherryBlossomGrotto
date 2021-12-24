package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.BigCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.BigCherryBlossomTrunkPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class BigCherryBlossomTreeConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {
    public BigCherryBlossomTreeConfiguredFeature() {
        super(Feature.TREE, (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new BigCherryBlossomFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0)),
                new BigCherryBlossomTrunkPlacer(9, 2, 3),
                new TwoLayerFeature(1, 1, 2))
        ).ignoreVines().build());
    }
}
