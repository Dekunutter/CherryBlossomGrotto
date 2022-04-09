package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.FancyCherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.GrandCherryBlossomTrunkPlacer;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.OptionalInt;

public class ModTreeFeatures {
    public static TreeConfiguration.TreeConfigurationBuilder createCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new CherryBlossomTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new CherryBlossomFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1)),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    public static TreeConfiguration.TreeConfigurationBuilder createFancyCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new FancyCherryBlossomTrunkPlacer(5, 3, 1),
                BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 2),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines();
    }

    public static TreeConfiguration.TreeConfigurationBuilder createGrandCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
                new GrandCherryBlossomTrunkPlacer(9, 2, 3),
                BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
                new GrandCherryBlossomFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }
}
