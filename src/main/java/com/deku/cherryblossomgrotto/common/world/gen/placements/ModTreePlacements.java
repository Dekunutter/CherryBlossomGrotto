package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModTreePlacements {
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_CHECKED = PlacementUtils.register("cherry_blossom_checked", ModTreeFeatures.CHERRY_BLOSSOM, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_002 = PlacementUtils.register("cherry_blossom_bees_002", ModTreeFeatures.CHERRY_BLOSSOM_BEES_002, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_02 = PlacementUtils.register("cherry_blossom_bees_02", ModTreeFeatures.CHERRY_BLOSSOM_BEES_02, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_05 = PlacementUtils.register("cherry_blossom_bees_05", ModTreeFeatures.CHERRY_BLOSSOM_BEES_05, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> FANCY_CHERRY_BLOSSOM_CHECKED = PlacementUtils.register("fancy_cherry_blossom_checked", ModTreeFeatures.FANCY_CHERRY_BLOSSOM, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> FANCY_CHERRY_BLOSSOM_BEES_05 = PlacementUtils.register("fancy_cherry_blossom_bees_05", ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
    public static final Holder<PlacedFeature> GRAND_CHERRY_BLOSSOM_CHECKED = PlacementUtils.register("grand_cherry_blossom_checked", ModTreeFeatures.GRAND_CHERRY_BLOSSOM, PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING));
}
