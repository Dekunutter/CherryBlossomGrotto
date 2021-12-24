package com.deku.cherryblossomgrotto.common.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.feature.*;

public class CherryBlossomTreeBees0002ConfiguredFeature extends ConfiguredFeature<BaseTreeFeatureConfig, Feature<BaseTreeFeatureConfig>> {
    public CherryBlossomTreeBees0002ConfiguredFeature() {
        super(Feature.TREE, ModFeatures.CHERRY_TREE.config().withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002)));
    }
}
