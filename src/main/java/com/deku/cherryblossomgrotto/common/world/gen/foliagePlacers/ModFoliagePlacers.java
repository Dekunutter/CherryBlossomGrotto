package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModFoliagePlacers {
    @ObjectHolder("cherry_blossom_tree_foliage_placer")
    public static FoliagePlacerType<CherryBlossomFoliagePlacer> CHERRY_TREE_FOLIAGE_PLACER;
    
    @ObjectHolder("big_cherry_blossom_tree_foliage_placer")
    public static FoliagePlacerType<GrandCherryBlossomFoliagePlacer> GRAND_CHERRY_TREE_FOLIAGE_PLACER;
}