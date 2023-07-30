package com.deku.eastwardjourneys.common.world.gen.foliagePlacers;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModFoliagePlacers {
    @ObjectHolder(registryName = "minecraft:worldgen/foliage_placer_type", value = MOD_ID + ":black_pine_foliage_placer")
    public static FoliagePlacerType<BlackPineFoliagePlacer> BLACK_PINE_FOLIAGE_PLACER;

    @ObjectHolder(registryName = "minecraft:worldgen/foliage_placer_type", value = MOD_ID + ":saxaul_foliage_placer")
    public static FoliagePlacerType<SaxaulFoliagePlacer> SAXAUL_FOLIAGE_PLACER;
}