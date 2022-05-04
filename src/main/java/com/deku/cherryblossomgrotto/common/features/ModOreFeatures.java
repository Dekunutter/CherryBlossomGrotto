package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;
import static net.minecraft.data.worldgen.features.OreFeatures.*;

public class ModOreFeatures {
    public static ConfiguredFeature<OreConfiguration, ?> ORE_IRON_SPARSE;

    /**
     * Creates an ore configuration for sparse iron
     *
     * @return The ore configuration for sparse iron
     */
    private static OreConfiguration createSparseIronOre() {
        return new OreConfiguration(ORE_IRON_TARGET_LIST, 2, 0.2F);
    }

    /**
     * Registers all ore configurations into the game via the configured feature registry
     */
    public static void register() {
        ORE_IRON_SPARSE = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "ore_iron_sparse"),
            new ConfiguredFeature<>(Feature.ORE, createSparseIronOre())
        );
    }
}
