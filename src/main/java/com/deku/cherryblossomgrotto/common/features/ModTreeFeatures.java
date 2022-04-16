package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.FancyCherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.GrandCherryBlossomTrunkPlacer;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;

import java.util.List;
import java.util.OptionalInt;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreeFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BLOSSOM = FeatureUtils.register(MOD_ID + ":cherry_blossom", Feature.TREE, createCherryBlossomTree().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BLOSSOM_BEES_002 = FeatureUtils.register(MOD_ID + ":cherry_blossom_bees_002", Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.002F))).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BLOSSOM_BEES_02 = FeatureUtils.register(MOD_ID + ":cherry_blossom_bees_02", Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.02F))).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_BLOSSOM_BEES_05 = FeatureUtils.register(MOD_ID + ":cherry_blossom_bees_05", Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.05F))).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> FANCY_CHERRY_BLOSSOM = FeatureUtils.register(MOD_ID + ":fancy_cherry_blossom", Feature.TREE, createFancyCherryBlossomTree().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> FANCY_CHERRY_BLOSSOM_BEES_05 = FeatureUtils.register(MOD_ID + ":fancy+cherry_blossom_bees_05", Feature.TREE, createFancyCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.05F))).build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GRAND_CHERRY_BLOSSOM = FeatureUtils.register(MOD_ID + ":grand_cherry_blossom", Feature.TREE, createGrandCherryBlossomTree().build());

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
