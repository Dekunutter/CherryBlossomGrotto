package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModFoliagePlacers {
    @ObjectHolder(registryName = "minecraft:worldgen/foliage_placers", value = MOD_ID + ":cherry_blossom_tree_foliage_placer")
    public static FoliagePlacerType<CherryBlossomFoliagePlacer> CHERRY_TREE_FOLIAGE_PLACER;

    @ObjectHolder(registryName = "minecraft:worldgen/foliage_placers", value = MOD_ID + ":big_cherry_blossom_tree_foliage_placer")
    public static FoliagePlacerType<GrandCherryBlossomFoliagePlacer> GRAND_CHERRY_TREE_FOLIAGE_PLACER;
}