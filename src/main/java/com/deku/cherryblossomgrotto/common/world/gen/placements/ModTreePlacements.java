package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreePlacements {
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("cherry_blossom_checked");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_002 = registerTreePlacementKey("cherry_blossom_bees_002");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_02 = registerTreePlacementKey("cherry_blossom_bees_02");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_05 = registerTreePlacementKey("cherry_blossom_bees_05");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_ON_SNOW = registerTreePlacementKey("cherry_blossom_on_snow");
    public static ResourceKey<PlacedFeature> FANCY_CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("fancy_cherry_blossom_checked");
    public static ResourceKey<PlacedFeature> FANCY_CHERRY_BLOSSOM_BEES_05 = registerTreePlacementKey("fancy_cherry_blossom_bees_05");
    public static ResourceKey<PlacedFeature> FANCY_CHERRY_BLOSSOM_ON_SNOW = registerTreePlacementKey("fancy_cherry_blossom_on_snow");

    public static ResourceKey<PlacedFeature> GRAND_CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("grand_cherry_blossom_checked");

    public static ResourceKey<PlacedFeature> FANCY_MAPLE_CHECKED = registerTreePlacementKey("fancy_maple_checked");
    public static ResourceKey<PlacedFeature> FANCY_MAPLE_BEES = registerTreePlacementKey("fancy_maple_bees");
    public static ResourceKey<PlacedFeature> BLACK_PINE_CHECKED = registerTreePlacementKey("black_pine_checked");
    public static ResourceKey<PlacedFeature> STRAIGHT_BLACK_PINE_CHECKED = registerTreePlacementKey("straight_black_pine_checked");
    public static ResourceKey<PlacedFeature> BRANCHING_BLACK_PINE_CHECKED = registerTreePlacementKey(" branching_black_pine_checked");

    /**
     * Registers the tree placements into the vanilla game by the placed feature registry
     *
     * @param placementName The registry name of the placed feature
     * @return The registered key for the custom placed feature
     */
    public static ResourceKey<PlacedFeature> registerTreePlacementKey(String placementName) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, placementName));
    }

    /**
     * Bootstraps the context needed to register the placed features for the mod
     *
     * @param context Bootstrap context needed to register placed features to the game
     */
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> featureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(CHERRY_BLOSSOM_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(CHERRY_BLOSSOM_BEES_002, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM_BEES_002), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(CHERRY_BLOSSOM_BEES_02, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM_BEES_02), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(CHERRY_BLOSSOM_BEES_05, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM_BEES_05), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(CHERRY_BLOSSOM_ON_SNOW, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM), surviveOnSnowPredicate()));
        context.register(FANCY_CHERRY_BLOSSOM_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_CHERRY_BLOSSOM), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(FANCY_CHERRY_BLOSSOM_BEES_05, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(FANCY_CHERRY_BLOSSOM_ON_SNOW, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_CHERRY_BLOSSOM), surviveOnSnowPredicate()));
        context.register(GRAND_CHERRY_BLOSSOM_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.GRAND_CHERRY_BLOSSOM), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));

        context.register(FANCY_MAPLE_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_MAPLE_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAPLE_SAPLING))));
        context.register(FANCY_MAPLE_BEES, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_MAPLE_TREE_BEES), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAPLE_SAPLING))));

        context.register(BLACK_PINE_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.BLACK_PINE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.BLACK_PINE_SAPLING))));
        context.register(STRAIGHT_BLACK_PINE_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.STRAIGHT_BLACK_PINE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.BLACK_PINE_SAPLING))));
        context.register(BRANCHING_BLACK_PINE_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.BRANCHING_BLACK_PINE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.BLACK_PINE_SAPLING))));
    }

    /**
     * Builds a list of placement modifiers that check if the tree can spawn on snow.
     * Only passes if the block the tree is growing from is a type of snow and that there isn't 8 or more blocks of powdered snow in the way

     * @return The list of placement modifiers that will determine if a tree can be spawned
     */
    private static List<PlacementModifier> surviveOnSnowPredicate() {
        BlockPredicate snowPredicate = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);
        return List.of(EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8), BlockPredicateFilter.forPredicate(snowPredicate));
    }
}
