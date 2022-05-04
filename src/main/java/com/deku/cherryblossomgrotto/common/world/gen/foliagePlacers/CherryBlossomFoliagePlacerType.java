package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class CherryBlossomFoliagePlacerType extends FoliagePlacerType {
    public CherryBlossomFoliagePlacerType() {
        super(CherryBlossomFoliagePlacer.CODEC);
        setRegistryName("cherry_blossom_tree_foliage_placer");
    }
}
