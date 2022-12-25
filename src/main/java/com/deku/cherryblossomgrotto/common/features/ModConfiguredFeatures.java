package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {
    /**
     * Loads all different configured features for this mod, providing them with a bootstrap context
     *
     * @param context The bootstrap context we'll use to initialize configured features
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ModMiscOverworldFeatures.bootstrap(context);
        ModOreFeatures.bootstrap(context);
        ModTreeFeatures.bootstrap(context);
        ModVegetationFeatures.bootstrap(context);
    }
}