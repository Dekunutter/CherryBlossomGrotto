package com.deku.cherryblossomgrotto.common.items;

import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModItems {
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":cherry_blossom_petal")
    public static CherryBlossomPetal CHERRY_PETAL;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":cherry_blossom_boat")
    public static CherryBlossomBoat CHERRY_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":katana")

    public static Katana KATANA;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kunai")
    public static Kunai KUNAI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":shuriken")
    public static Shuriken SHURIKEN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":rice")
    public static Rice RICE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":onigiri")
    public static Onigiri ONIGIRI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":koi_bucket")
    public static KoiBucket KOI_BUCKET;
}
