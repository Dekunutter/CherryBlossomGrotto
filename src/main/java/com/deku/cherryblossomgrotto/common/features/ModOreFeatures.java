package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;
import static net.minecraft.data.worldgen.features.OreFeatures.*;

public class ModOreFeatures {
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_IRON_SPARSE = FeatureUtils.register(
        MOD_ID + ":ore_iron_sparse",
        Feature.ORE,
        createSparseIronOre()
    );

    public static OreConfiguration createSparseIronOre() {
        return new OreConfiguration(ORE_IRON_TARGET_LIST, 2, 0.2F);
    }
}
