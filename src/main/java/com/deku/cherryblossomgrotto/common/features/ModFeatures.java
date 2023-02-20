package com.deku.cherryblossomgrotto.common.features;

import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModFeatures {
    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":cherry_blossom_petal_ground_cover")
    public static CherryBlossomPetalCoverFeature CHERRY_BLOSSOM_GROUND_COVER;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":maple_leaf_ground_cover")
    public static MapleLeafPileCoverFeature MAPLE_GROUND_COVER;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":hotspring")
    public static HotspringFeature HOTSPRING;
}
