package com.deku.cherryblossomgrotto.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> maxFolds;

    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnCherryBlossomBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnMapleBiomes;

    static {
        BUILDER.comment("------ Cherry Blossom Grotto General Settings ------").push("cherry_blossom_grotto");
            BUILDER.push("biomes");
                spawnMapleBiomes = BUILDER.comment("Whether maple tree biomes should spawn").define("mapleBiomes", true);
                spawnCherryBlossomBiomes = BUILDER.comment("Whether cherry blossom biomes should spawn").define("cherryBlossomBiomes", true);
        BUILDER.pop();
            BUILDER.push("weapons");
                maxFolds = BUILDER.comment("Maximum number of times that the katana can be folded").defineInRange("folds", 1000, 1, 1000000);
            BUILDER.pop();
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}
