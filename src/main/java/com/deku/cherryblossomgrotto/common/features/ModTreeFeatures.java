package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.BlackPineFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.BlackPineTrunkPlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreeFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM = registerTreeFeatureKey("cherry_blossom");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_BEES_002 = registerTreeFeatureKey("cherry_blossom_bees_002");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_BEES_02 = registerTreeFeatureKey("cherry_blossom_bees_02");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_BEES_05 = registerTreeFeatureKey("cherry_blossom_bees_05");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_CHERRY_BLOSSOM = registerTreeFeatureKey("fancy_cherry_blossom");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_CHERRY_BLOSSOM_BEES_05 = registerTreeFeatureKey("fancy_cherry_blossom_bees_05");
    public static ResourceKey<ConfiguredFeature<?, ?>> GRAND_CHERRY_BLOSSOM = registerTreeFeatureKey("grand_cherry_blossom");

    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_TREE = registerTreeFeatureKey("fancy_maple");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_TREE_BEES = registerTreeFeatureKey("fancy_maple_bees");
    public static ResourceKey<ConfiguredFeature<?, ?>> BLACK_PINE = registerTreeFeatureKey("black_pine");
    public static ResourceKey<ConfiguredFeature<?, ?>> STRAIGHT_BLACK_PINE = registerTreeFeatureKey("straight_black_pine");
    public static ResourceKey<ConfiguredFeature<?, ?>> BRANCHING_BLACK_PINE = registerTreeFeatureKey("branching_black_pine");


    /**
     * Registers a resource key for the given tree feature name
     *
     * @param featureName Name of the feature we want to create a resource key for
     * @return The resource key created for the given feature
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerTreeFeatureKey(String featureName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MOD_ID, featureName));
    }

    /**
     * Configures a maple tree
     *
     * @return The configuration for a maple tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createFancyMapleTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG),
                new FancyTrunkPlacer(3, 11, 0),
                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBlackPineTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LOG),
                new BlackPineTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LEAVES),
                new BlackPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(1)),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlackPineTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LOG),
                new StraightTrunkPlacer(5, 2, 1),
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LEAVES),
                new SpruceFoliagePlacer(UniformInt.of(2, 0), UniformInt.of(3, 2), UniformInt.of(2, 1)),
                new TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBranchingBlackPineTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LOG),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(ModBlocks.BLACK_PINE_LEAVES),
                new AcaciaFoliagePlacer(ConstantInt.of(0), ConstantInt.of(2)),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    /**
     * Registers tree features using the bootstrap context
     *
     * @param context The bootstrap context
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        BeehiveDecorator beehiveDecorator002 = new BeehiveDecorator(0.002F);
        BeehiveDecorator beehiveDecorator02 = new BeehiveDecorator(0.02F);
        BeehiveDecorator beehiveDecorator05 = new BeehiveDecorator(0.05F);
        BeehiveDecorator beehiveDecorator25 = new BeehiveDecorator(0.25F);

        context.register(FANCY_MAPLE_TREE_BEES, new ConfiguredFeature<>(Feature.TREE, createFancyMapleTreeConfiguration().decorators(ImmutableList.of(beehiveDecorator25)).build()));

        context.register(BLACK_PINE, new ConfiguredFeature<>(Feature.TREE, createBlackPineTreeConfiguration().build()));
        context.register(STRAIGHT_BLACK_PINE, new ConfiguredFeature<>(Feature.TREE, createStraightBlackPineTreeConfiguration().build()));
        context.register(BRANCHING_BLACK_PINE, new ConfiguredFeature<>(Feature.TREE, createBranchingBlackPineTreeConfiguration().build()));
    }
}
