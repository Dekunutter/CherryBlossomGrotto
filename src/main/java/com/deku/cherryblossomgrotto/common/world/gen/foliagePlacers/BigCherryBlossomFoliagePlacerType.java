package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class BigCherryBlossomFoliagePlacerType extends FoliagePlacerType {
    public BigCherryBlossomFoliagePlacerType() {
        super(BigCherryBlossomFoliagePlacer.CODEC);
        setRegistryName("big_cherry_blossom_tree_foliage_placer");
    }
}
