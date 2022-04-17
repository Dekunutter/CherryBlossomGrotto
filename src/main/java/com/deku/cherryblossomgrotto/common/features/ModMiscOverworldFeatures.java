package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModMiscOverworldFeatures {
    public static ConfiguredFeature<NoneFeatureConfiguration, ?> CHERRY_BLOSSOM_PETAL_GROUND_COVER;

    public static void register() {
        CHERRY_BLOSSOM_PETAL_GROUND_COVER = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom_petal_ground_cover"),
            new ConfiguredFeature<>(ModFeatures.CHERRY_BLOSSOM_GROUND_COVER, FeatureConfiguration.NONE)
        );
    }
}
