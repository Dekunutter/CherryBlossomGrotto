package com.deku.eastwardjourneys.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ModConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> maxFolds;

    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnCherryBlossomBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnMapleBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnBlackPineBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnStoneForestBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnHinokiBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnWaterFirBiomes;
    public static final ForgeConfigSpec.ConfigValue<Boolean> spawnShrublands;

    public static final ForgeConfigSpec.ConfigValue<Integer> fallingLeafParticleSpawnChance;
    public static final ForgeConfigSpec.ConfigValue<Integer> leafPileSpawnChance;

    static {
        BUILDER.comment("------ Eastward Journeys General Settings ------").push("eastward_journeys");
            BUILDER.push("biomes");
                spawnStoneForestBiomes = BUILDER.comment("Whether stone forest biomes should spawn").define("stoneForestBiomes", true);
                spawnBlackPineBiomes = BUILDER.comment("Whether black pine tree biomes should spawn").define("blackPineBiomes", true);
                spawnMapleBiomes = BUILDER.comment("Whether maple tree biomes should spawn").define("mapleBiomes", true);
                spawnCherryBlossomBiomes = BUILDER.comment("Whether cherry blossom biomes should spawn").define("cherryBlossomBiomes", true);
                spawnHinokiBiomes = BUILDER.comment("Whether hinoki biomes should spawn").define("hinokiBiomes", true);
                spawnWaterFirBiomes = BUILDER.comment("Whether water fir biomes should spawn").define("waterFirBiomes", true);
                spawnShrublands = BUILDER.comment("Whether shrubland biomes should spawn").define("shrublandBiomes", true);
                fallingLeafParticleSpawnChance = BUILDER.comment("The chance a falling leaf will spawn from a cherry or maple leaves block. The higher the number, the slower the falling leaf particles will spawn, and therefore the slower leaf piles will accumulate underneath").defineInRange("fallingLeafParticleSpawnChance", 32, 1, 1000000);
                leafPileSpawnChance = BUILDER.comment("The chance a falling leaf particle has of spawning a leaf pile on the ground for cherry and maple trees. The higher the number, the slower the leaf piles will accumulate. Combines with fallingLeafParticleSpawnChance").defineInRange("leafPileSpawnChance", 500, 1, 1000000);
        BUILDER.pop();
            BUILDER.push("weapons");
                maxFolds = BUILDER.comment("Maximum number of times that the katana can be folded").defineInRange("folds", 1000, 1, 1000000);
            BUILDER.pop();
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}
