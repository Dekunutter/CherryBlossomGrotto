package com.deku.cherryblossomgrotto.common.features;

import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModFeatures {
    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":maple_leaf_ground_cover")
    public static MapleLeafPileCoverFeature MAPLE_GROUND_COVER;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":hotspring")
    public static HotspringFeature HOTSPRING;

    @ObjectHolder(registryName = "minecrarft:worldgen/feature", value = MOD_ID + ":karst_stone")
    public static KarstStoneFeature KARST_STONE;
}
