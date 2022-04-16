package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModVillagePlacements {
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_TREE_VILLAGE = PlacementUtils.register("cherry_blossom", ModTreeFeatures.CHERRY_BLOSSOM, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> FLOWER_FOREST_VILLAGE = PlacementUtils.register("flower_forest", VegetationFeatures.FLOWER_FLOWER_FOREST);
}
