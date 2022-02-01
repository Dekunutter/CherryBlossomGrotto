package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class CherryBlossomTreeConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {

    public CherryBlossomTreeConfiguredFeature() {
        super(Feature.TREE, (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new SimpleBlockStateProvider(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new CherryBlossomFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1)),
                new CherryBlossomTrunkPlacer(4, 2, 2),
                new TwoLayerFeature(1, 0, 2))
        ).ignoreVines().build());
    }
}
