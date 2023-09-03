package com.deku.eastwardjourneys.common.features;

import net.minecraftforge.registries.ObjectHolder;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModFeatures {
    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":maple_leaf_ground_cover")
    public static MapleLeafPileCoverFeature MAPLE_GROUND_COVER;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":moss_layer")
    public static MossLayerFeature MOSS_LAYER;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":hotspring")
    public static HotspringFeature HOTSPRING;

    @ObjectHolder(registryName = "minecrarft:worldgen/feature", value = MOD_ID + ":karst_stone")
    public static KarstStoneFeature KARST_STONE;

    @ObjectHolder(registryName = "minecrarft:worldgen/feature", value = MOD_ID + ":fallen_tree")
    public static FallenTreeFeature FALLEN_TREE;

    @ObjectHolder(registryName = "minecrarft:worldgen/feature", value = MOD_ID + ":huge_enoki_mushroom")
    public static HugeEnokiMushroomFeature HUGE_ENOKI_MUSHROOM;

    @ObjectHolder(registryName = "minecrarft:worldgen/feature", value = MOD_ID + ":huge_shiitake_mushroom")
    public static HugeShiitakeMushroomFeature HUGE_SHIITAKE_MUSHROOM;

    @ObjectHolder(registryName = "minecraft:worldgen/feature", value = MOD_ID + ":rice_paddy")
    public static RicePaddyFeature RICE_PADDY;
}
