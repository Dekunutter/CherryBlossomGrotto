package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.world.gen.placements.ModTreePlacements;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVegetationFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_CHERY_BLOSSOM_GROTTO = registerVegetationFeatureKey("trees_cherry_blossom_grotto");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERY_BLOSSOM_GROTTO_FLOWERS = registerVegetationFeatureKey("cherry_blossom_grotto_flowers");
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_CHERY_BLOSSOM_SLOPES = registerVegetationFeatureKey("trees_cherry_blossom_slopes");
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_CHERY_BLOSSOM_SPARSE = registerVegetationFeatureKey("trees_cherry_blossom_sparse");
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_BAMBOO_VEGETATION = registerVegetationFeatureKey("cherry_blossom_bamboo_vegetation");
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_MAPLE_WOODS = registerVegetationFeatureKey("trees_maple_woods");
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_OAK_AND_MAPLE_FOREST = registerVegetationFeatureKey("trees_oak_and_maple_forest");

    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_BLACK_PINE_FOREST = registerVegetationFeatureKey("trees_black_pine_forest");

    /**
     * Registers a resource key for the given vegetation feature name
     *
     * @param featureName Name of the feature we want to create a resource key for
     * @return The resource key created for the given feature
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerVegetationFeatureKey(String featureName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MOD_ID, featureName));
    }

    /**
     * Creates a feature configuration for the cherry blossom trees
     *
     * @param cherryBlossomTreeBees02 Holder for the placeable cherry blossom trees (with bees) feature
     * @param fancyCherryBlossomTree Holder for the placeable fancy cherry blossom trees feature
     * @param grandCherryBlossomTree Holder for the placeable grand cherry blossom trees feature
     * @return Random feature configuration for spreading the trees
     */
    private static RandomFeatureConfiguration createCherryBlossomGrottoTreesConfiguration(Holder<PlacedFeature> cherryBlossomTreeBees02, Holder<PlacedFeature> fancyCherryBlossomTree, Holder<PlacedFeature> grandCherryBlossomTree) {
        return new RandomFeatureConfiguration(
            ImmutableList.of(
                new WeightedPlacedFeature(grandCherryBlossomTree, 0.01f),
                new WeightedPlacedFeature(fancyCherryBlossomTree, 0.1f)
            ),
            cherryBlossomTreeBees02
        );
    }

    /**
     * Creates a feature configuration for the cherry blossom grotto flowers
     *
     * @return Random feature configuration for spreading the flowers in a cherry blossom grotto
     */
    private static SimpleRandomFeatureConfiguration createCherryBlossomGrottoFlowersConfiguration() {
        return new SimpleRandomFeatureConfiguration(
            HolderSet.direct(
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.NO_BONEMEAL_FLOWER,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM)))
                )
            )
        );
    }

    /**
     * Creates a feature configuration for the sparse cherry blossom trees on slopes
     *
     * @param cherryBlossomTree Holder for the placeable cherry blossom trees feature
     * @param fancyCherryBlossomTree Holder for the placeable fancy cherry blossom trees feature
     * @return Random feature configuration for spreading the trees
     */
    private static RandomFeatureConfiguration createCherryBlossomSlopesTreesConfiguration(Holder<PlacedFeature> cherryBlossomTree, Holder<PlacedFeature> fancyCherryBlossomTree) {
        return new RandomFeatureConfiguration(
            ImmutableList.of(
                new WeightedPlacedFeature(fancyCherryBlossomTree, 0.1f)
            ),
            cherryBlossomTree
        );
    }

    /**
     * Creates a feature configuration for the sparse cherry blossom trees
     *
     * @param cherryBlossomTree Holder for the placeable cherry blossom trees feature
     * @param fancyCherryBlossomTree Holder for the placeable fancy cherry blossom trees feature
     * @return Random feature configuration for spreading the trees
     */
    private static RandomFeatureConfiguration createCherryBlossomSparseTreesConfiguration(Holder<PlacedFeature> cherryBlossomTree, Holder<PlacedFeature> fancyCherryBlossomTree) {
        return new RandomFeatureConfiguration(
                ImmutableList.of(
                        new WeightedPlacedFeature(fancyCherryBlossomTree, 0.1f)
                ),
                cherryBlossomTree
        );
    }

    /**
     * Creates a feature configuration for the sparse cherry blossom trees
     *
     * @param cherryBlossomTree Holder for the placeable cherry blossom trees feature
     * @param fancyCherryBlossomTree Holder for the placeable fancy cherry blossom trees feature
     * @return Random feature configuration for spreading the trees
     */
    private static RandomFeatureConfiguration createCherryBlossomBambooVegetationConfiguration(Holder<ConfiguredFeature<?, ?>> jungleGrass, Holder<PlacedFeature> cherryBlossomTree, Holder<PlacedFeature> fancyCherryBlossomTree, Holder<PlacedFeature> jungleBush) {
        return new RandomFeatureConfiguration(
            ImmutableList.of(
                new WeightedPlacedFeature(fancyCherryBlossomTree, 0.05f),
                new WeightedPlacedFeature(jungleBush, 0.15F),
                new WeightedPlacedFeature(cherryBlossomTree, 0.7F)
            ),
            PlacementUtils.inlinePlaced(jungleGrass)
        );
    }

    private static RandomFeatureConfiguration createMapleTreesConfiguration(Holder<PlacedFeature> fancyMapleTree, Holder<PlacedFeature> fancyMapleTreeBees) {
        return new RandomFeatureConfiguration(
            ImmutableList.of(
                new WeightedPlacedFeature(fancyMapleTreeBees, 0.1f)
            ),
            fancyMapleTree
        );
    }

    private static RandomFeatureConfiguration createOakAndMapleTreesConfiguration(Holder<PlacedFeature> fancyMapleTree, Holder<PlacedFeature> fancyMapleTreeBees, Holder<PlacedFeature> oakTreeBees002, Holder<PlacedFeature> fancyOakTreeBees002) {
        return new RandomFeatureConfiguration(
                ImmutableList.of(
                        new WeightedPlacedFeature(fancyMapleTree, 0.2f),
                        new WeightedPlacedFeature(fancyMapleTreeBees, 0.1f),
                        new WeightedPlacedFeature(oakTreeBees002, 0.1f)
                ),
                fancyOakTreeBees002
        );
    }

    private static RandomFeatureConfiguration createBlackPineTreesConfiguration(Holder<PlacedFeature> wobblyBlackPine, Holder<PlacedFeature> straightBlackPine, Holder<PlacedFeature> branchingBlackPine) {
        return new RandomFeatureConfiguration(
                ImmutableList.of(
                        new WeightedPlacedFeature(wobblyBlackPine, 0.5f),
                        new WeightedPlacedFeature(straightBlackPine, 0.3f),
                        new WeightedPlacedFeature(branchingBlackPine, 0.2f)
                ),
                wobblyBlackPine
        );
    }

    /**
     * Registers vegetation features using the bootstrap context
     *
     * @param context The bootstrap context
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<PlacedFeature> placedFeatureGetter = context.lookup(Registries.PLACED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> jungleGrass = configuredFeatureGetter.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE);

        Holder<PlacedFeature> cherryBlossomTree = placedFeatureGetter.getOrThrow(ModTreePlacements.CHERRY_BLOSSOM_CHECKED);
        Holder<PlacedFeature> cherryBlossomTreeBees02 = placedFeatureGetter.getOrThrow(ModTreePlacements.CHERRY_BLOSSOM_BEES_02);
        Holder<PlacedFeature> cherryBlossomTreeOnSnow = placedFeatureGetter.getOrThrow(ModTreePlacements.CHERRY_BLOSSOM_ON_SNOW);
        Holder<PlacedFeature> fancyCherryBlossomTree = placedFeatureGetter.getOrThrow(ModTreePlacements.FANCY_CHERRY_BLOSSOM_CHECKED);
        Holder<PlacedFeature> fancyCherryBlossomTreeOnSnow = placedFeatureGetter.getOrThrow(ModTreePlacements.FANCY_CHERRY_BLOSSOM_ON_SNOW);
        Holder<PlacedFeature> grandCherryBlossomTree = placedFeatureGetter.getOrThrow(ModTreePlacements.GRAND_CHERRY_BLOSSOM_CHECKED);
        Holder<PlacedFeature> fancyMapleTree = placedFeatureGetter.getOrThrow(ModTreePlacements.FANCY_MAPLE_CHECKED);
        Holder<PlacedFeature> fancyMapleTreeBees = placedFeatureGetter.getOrThrow(ModTreePlacements.FANCY_MAPLE_BEES);
        Holder<PlacedFeature> jungleBush = placedFeatureGetter.getOrThrow(TreePlacements.JUNGLE_BUSH);
        Holder<PlacedFeature> oakTreeBees002 = placedFeatureGetter.getOrThrow(TreePlacements.OAK_BEES_002);
        Holder<PlacedFeature> fancyOakTreeBees002 = placedFeatureGetter.getOrThrow(TreePlacements.FANCY_OAK_BEES_002);
        Holder<PlacedFeature> wobblyBlackPine = placedFeatureGetter.getOrThrow(ModTreePlacements.BLACK_PINE_CHECKED);
        Holder<PlacedFeature> straightBlackPine = placedFeatureGetter.getOrThrow(ModTreePlacements.STRAIGHT_BLACK_PINE_CHECKED);
        Holder<PlacedFeature> branchingBlackPine = placedFeatureGetter.getOrThrow(ModTreePlacements.BRANCHING_BLACK_PINE_CHECKED);

        context.register(TREES_CHERY_BLOSSOM_GROTTO, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createCherryBlossomGrottoTreesConfiguration(cherryBlossomTreeBees02, fancyCherryBlossomTree, grandCherryBlossomTree)));
        context.register(CHERY_BLOSSOM_GROTTO_FLOWERS, new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, createCherryBlossomGrottoFlowersConfiguration()));
        context.register(TREES_CHERY_BLOSSOM_SLOPES, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createCherryBlossomSlopesTreesConfiguration(cherryBlossomTreeOnSnow, fancyCherryBlossomTreeOnSnow)));
        context.register(TREES_CHERY_BLOSSOM_SPARSE, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createCherryBlossomSparseTreesConfiguration(cherryBlossomTreeBees02, fancyCherryBlossomTree)));
        context.register(CHERRY_BLOSSOM_BAMBOO_VEGETATION, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createCherryBlossomBambooVegetationConfiguration(jungleGrass, cherryBlossomTree, fancyCherryBlossomTree, jungleBush)));
        context.register(TREES_MAPLE_WOODS, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createMapleTreesConfiguration(fancyMapleTree, fancyMapleTreeBees)));
        context.register(TREES_OAK_AND_MAPLE_FOREST, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createOakAndMapleTreesConfiguration(fancyMapleTree, fancyMapleTreeBees, oakTreeBees002, fancyOakTreeBees002)));
        context.register(TREES_BLACK_PINE_FOREST, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, createBlackPineTreesConfiguration(wobblyBlackPine, straightBlackPine, branchingBlackPine)));
    }
}
