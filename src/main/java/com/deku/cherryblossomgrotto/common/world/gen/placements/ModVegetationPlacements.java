package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModVegetationFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVegetationPlacements {
    public static ResourceKey<PlacedFeature> TREES_CHERRY_BLOSSOM_GROTTO = registerVegetationPlacementKey("trees_cherry_blossom_grotto");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_GROTTO_FLOWERS = registerVegetationPlacementKey("cherry_blossom_grotto_flowers");
    public static ResourceKey<PlacedFeature> TREES_CHERRY_BLOSSOM_SLOPES = registerVegetationPlacementKey("trees_cherry_blossom_slopes");
    public static ResourceKey<PlacedFeature> TREES_CHERRY_BLOSSOM_SPARSE = registerVegetationPlacementKey("trees_cherry_blossom_sparse");
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_BAMBOO_VEGETATION = registerVegetationPlacementKey("cherry_blossom_bamboo_vegetation");

    /**
     * Registers the vegetation placements into the vanilla game by the placed feature registry
     *
     * @param placementName The registry name of the placed feature
     * @return The registered key for the custom placed feature
     */
    public static ResourceKey<PlacedFeature> registerVegetationPlacementKey(String placementName) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, placementName));
    }

    /**
     * Bootstraps the context needed to register the placed features for the mod
     *
     * @param context Bootstrap context needed to register placed features to the game
     */
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> featureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(TREES_CHERRY_BLOSSOM_GROTTO, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_CHERY_BLOSSOM_GROTTO), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))));
        context.register(CHERRY_BLOSSOM_GROTTO_FLOWERS, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.CHERY_BLOSSOM_GROTTO_FLOWERS), List.of(RarityFilter.onAverageOnceEvery(7),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
            BiomeFilter.biome())));
        context.register(TREES_CHERRY_BLOSSOM_SLOPES, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_CHERY_BLOSSOM_SLOPES), VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))));
        context.register(TREES_CHERRY_BLOSSOM_SPARSE, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_CHERY_BLOSSOM_GROTTO), VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))));
        context.register(CHERRY_BLOSSOM_BAMBOO_VEGETATION, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.CHERRY_BLOSSOM_BAMBOO_VEGETATION), VegetationPlacements.treePlacement(PlacementUtils.countExtra(30, 0.1F, 1))));
    }
}
