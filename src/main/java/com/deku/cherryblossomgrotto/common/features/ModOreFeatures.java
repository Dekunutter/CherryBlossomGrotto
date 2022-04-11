package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import static net.minecraft.data.worldgen.features.OreFeatures.*;

public class ModOreFeatures {
    public static OreConfiguration createSparseIronOre() {
        return new OreConfiguration(ORE_IRON_TARGET_LIST, 2, 0.2F);
    }
}
