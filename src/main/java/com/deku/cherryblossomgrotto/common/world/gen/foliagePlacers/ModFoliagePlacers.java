package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModFoliagePlacers {
    @ObjectHolder("cherry_blossom_tree_foliage_placer")
    public static CherryBlossomFoliagePlacerType CHERRY_TREE_FOLIAGE_PLACER;
    
    @ObjectHolder("big_cherry_blossom_tree_foliage_placer")
    public static GrandCherryBlossomFoliagePlacerType GRAND_CHERRY_TREE_FOLIAGE_PLACER;
}