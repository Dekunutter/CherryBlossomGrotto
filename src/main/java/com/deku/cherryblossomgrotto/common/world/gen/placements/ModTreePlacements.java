package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreePlacements {
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("cherry_blossom_checked");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_002 = registerTreePlacementKey("cherry_blossom_bees_002");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_02 = registerTreePlacementKey("cherry_blossom_bees_02");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BEES_05 = registerTreePlacementKey("cherry_blossom_bees_05");
    public static ResourceKey<PlacedFeature> FANCY_CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("fancy_cherry_blossom_checked");
    public static ResourceKey<PlacedFeature> FANCY_CHERRY_BLOSSOM_BEES_05 = registerTreePlacementKey("fancy_cherry_blossom_bees_05");
    public static ResourceKey<PlacedFeature> GRAND_CHERRY_BLOSSOM_CHECKED = registerTreePlacementKey("grand_cherry_blossom_checked");

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
        context.register(FANCY_CHERRY_BLOSSOM_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_CHERRY_BLOSSOM), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(FANCY_CHERRY_BLOSSOM_BEES_05, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
        context.register(GRAND_CHERRY_BLOSSOM_CHECKED, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.GRAND_CHERRY_BLOSSOM), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING))));
    }
}
