package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModOrePlacements {
    public static ResourceKey<PlacedFeature> ORE_IRON_SPARSE = registerOrePlacementKey("ore_iron_sparse");
    public static ResourceKey<PlacedFeature> ORE_IRON_SPARSE_UPPER = registerOrePlacementKey("ore_iron_sparse_upper");

    /**
     * Registers the ore placements into the vanilla game by the placed feature registry
     *
     * @param placementName The registry name of the placed feature
     * @return The registered key for the custom placed feature
     */
    public static ResourceKey<PlacedFeature> registerOrePlacementKey(String placementName) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, placementName));
    }

    /**
     * Bootstraps the context needed to register the placed features for the mod
     *
     * @param context Bootstrap context needed to register placed features to the game
     */
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> featureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(ORE_IRON_SPARSE, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM), List.of(CountPlacement.of(10), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)), BiomeFilter.biome())));
        context.register(ORE_IRON_SPARSE_UPPER, new PlacedFeature(featureGetter.getOrThrow(ModTreeFeatures.CHERRY_BLOSSOM), List.of(CountPlacement.of(10), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)), BiomeFilter.biome())));
    }
}
