package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.FancyCherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.GrandCherryBlossomTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreeFeatures {
    public static ConfiguredFeature<TreeConfiguration, ?> CHERRY_BLOSSOM;
    public static ConfiguredFeature<TreeConfiguration, ?> CHERRY_BLOSSOM_BEES_002;
    public static ConfiguredFeature<TreeConfiguration, ?> CHERRY_BLOSSOM_BEES_02;
    public static ConfiguredFeature<TreeConfiguration, ?> CHERRY_BLOSSOM_BEES_05;
    public static ConfiguredFeature<TreeConfiguration, ?> FANCY_CHERRY_BLOSSOM;
    public static ConfiguredFeature<TreeConfiguration, ?> FANCY_CHERRY_BLOSSOM_BEES_05;
    public static ConfiguredFeature<TreeConfiguration, ?> GRAND_CHERRY_BLOSSOM;

    /**
     * Configures a cherry blossom tree
     *
     * @return The configuration for a cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
            new BendingTrunkPlacer(4, 2, 0, 4, UniformInt.of(1, 2)),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
            new CherryBlossomFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1)),
            new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    /**
     * Configures a fancy cherry blossom tree
     *
     * @return The configuration for a fancy cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createFancyCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
            new BendingTrunkPlacer(5, 3, 1, 6, UniformInt.of(1, 2)),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
            new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 2),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines();
    }

    /**
     * Configures a grand cherry blossom tree
     *
     * @return The configuration for a grand cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createGrandCherryBlossomTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG.defaultBlockState()),
            new GiantTrunkPlacer(9, 2, 3),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.defaultBlockState()),
            new GrandCherryBlossomFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)),
            new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    /**
     * Registers all tree configurations into the game via the configured feature registry
     */
    public static void register() {
        CHERRY_BLOSSOM = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom"),
            new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTree().build())
        );
        CHERRY_BLOSSOM_BEES_002 = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom_bees_002"),
            new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.002F))).build())
        );
        CHERRY_BLOSSOM_BEES_02 = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom_bees_02"),
            new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.02F))).build())
        );
        CHERRY_BLOSSOM_BEES_05 = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom_bees_05"),
            new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.05F))).build())
        );

        FANCY_CHERRY_BLOSSOM = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "fancy_cherry_blossom"),
            new ConfiguredFeature<>(Feature.TREE, createFancyCherryBlossomTree().build())
        );
        FANCY_CHERRY_BLOSSOM_BEES_05 = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "fancy_cherry_blossom_bees_05"),
            new ConfiguredFeature<>(Feature.TREE, createFancyCherryBlossomTree().decorators(List.of(new BeehiveDecorator(0.05F))).build())
        );

        GRAND_CHERRY_BLOSSOM = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "grand_cherry_blossom"),
            new ConfiguredFeature<>(Feature.TREE, createGrandCherryBlossomTree().build())
        );
    }
}
