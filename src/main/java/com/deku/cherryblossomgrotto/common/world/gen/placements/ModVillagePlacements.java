package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVillagePlacements {
    public static ResourceKey<PlacedFeature> CHERRY_BLOSSOM_TREE_VILLAGE = registerVillagePlacementKey("cherry_blossom");
    public static ResourceKey<PlacedFeature> FLOWER_FOREST_VILLAGE = registerVillagePlacementKey("flower_forest");

    /**
     * Registers the village placements into the vanilla game by the placed feature registry
     *
     * @param placementName The registry name of the placed feature
     * @return The registered key for the custom placed feature
     */
    public static ResourceKey<PlacedFeature> registerVillagePlacementKey(String placementName) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MOD_ID, placementName));
    }

    /**
     * Bootstraps the context needed to register the placed features for the mod
     *
     * @param context Bootstrap context needed to register placed features to the game
     */
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> featureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(CHERRY_BLOSSOM_TREE_VILLAGE, new PlacedFeature(featureGetter.getOrThrow(TreeFeatures.CHERRY), List.of(PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING))));
        context.register(FLOWER_FOREST_VILLAGE, new PlacedFeature(featureGetter.getOrThrow(VegetationFeatures.FLOWER_FLOWER_FOREST), List.of()));
    }
}
