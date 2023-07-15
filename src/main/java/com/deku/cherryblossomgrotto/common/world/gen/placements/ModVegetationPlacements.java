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
    public static ResourceKey<PlacedFeature> TREES_MAPLE_WOODS = registerVegetationPlacementKey("trees_maple_woods");
    public static ResourceKey<PlacedFeature> TREES_OAK_AND_MAPLE_FOREST = registerVegetationPlacementKey("trees_oak_and_maple_forest");

    public static ResourceKey<PlacedFeature> TREES_BLACK_PINE_FOREST = registerVegetationPlacementKey("trees_black_pine_forest");

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

        context.register(TREES_MAPLE_WOODS, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_MAPLE_WOODS), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))));
        context.register(TREES_OAK_AND_MAPLE_FOREST, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_OAK_AND_MAPLE_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))));
        context.register(TREES_BLACK_PINE_FOREST, new PlacedFeature(featureGetter.getOrThrow(ModVegetationFeatures.TREES_BLACK_PINE_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))));
    }
}
