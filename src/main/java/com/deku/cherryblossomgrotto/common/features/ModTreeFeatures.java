package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.CherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers.GrandCherryBlossomFoliagePlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.CherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.FancyCherryBlossomTrunkPlacer;
import com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers.GrandCherryBlossomTrunkPlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;

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
     * Configures a cherry blossom tree
     *
     * @return The configuration for a cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createCherryBlossomTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG),
            new CherryBlossomTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES),
            new CherryBlossomFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1)),
            new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    /**
     * Configures a fancy cherry blossom tree
     *
     * @return The configuration for a fancy cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createFancyCherryBlossomTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG),
            new FancyCherryBlossomTrunkPlacer(5, 3, 1),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES),
            new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 2),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
        ).ignoreVines();
    }

    /**
     * Configures a grand cherry blossom tree
     *
     * @return The configuration for a grand cherry blossom tree
     */
    private static TreeConfiguration.TreeConfigurationBuilder createGrandCherryBlossomTreeConfiguration() {
        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.CHERRY_LOG),
            new GrandCherryBlossomTrunkPlacer(9, 2, 3),
            BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES),
            new GrandCherryBlossomFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0)),
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

        context.register(CHERRY_BLOSSOM, new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTreeConfiguration().build()));
        context.register(CHERRY_BLOSSOM_BEES_002, new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTreeConfiguration().decorators(ImmutableList.of(beehiveDecorator002)).build()));
        context.register(CHERRY_BLOSSOM_BEES_02, new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTreeConfiguration().decorators(ImmutableList.of(beehiveDecorator02)).build()));
        context.register(CHERRY_BLOSSOM_BEES_05, new ConfiguredFeature<>(Feature.TREE, createCherryBlossomTreeConfiguration().decorators(ImmutableList.of(beehiveDecorator05)).build()));
        context.register(FANCY_CHERRY_BLOSSOM, new ConfiguredFeature<>(Feature.TREE, createFancyCherryBlossomTreeConfiguration().build()));
        context.register(FANCY_CHERRY_BLOSSOM_BEES_05, new ConfiguredFeature<>(Feature.TREE, createFancyCherryBlossomTreeConfiguration().decorators(ImmutableList.of(beehiveDecorator05)).build()));
        context.register(GRAND_CHERRY_BLOSSOM, new ConfiguredFeature<>(Feature.TREE, createGrandCherryBlossomTreeConfiguration().build()));
    }
}
