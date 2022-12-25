package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModMiscOverworldFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_PETAL_GROUND_COVER = registerOverworldFeatureKey("cherry_blossom_petal_ground_cover");

    /**
     * Registers a resource key for the given miscellanous overworld feature name
     *
     * @param featureName Name of the feature we want to create a resource key for
     * @return The resource key created for the given feature
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerOverworldFeatureKey(String featureName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MOD_ID, featureName));
    }

    /**
     * Registers miscellaneous overworld features using the bootstrap context
     *
     * @param context The bootstrap context
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CHERRY_BLOSSOM_PETAL_GROUND_COVER, new ConfiguredFeature<>(ModFeatures.CHERRY_BLOSSOM_GROUND_COVER, FeatureConfiguration.NONE));
    }
}
