package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTreePlacements {
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_CHECKED;
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_002;
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_02;
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_BEES_05;
    public static Holder<PlacedFeature> FANCY_CHERRY_BLOSSOM_CHECKED;
    public static Holder<PlacedFeature> FANCY_CHERRY_BLOSSOM_BEES_05;
    public static Holder<PlacedFeature> GRAND_CHERRY_BLOSSOM_CHECKED;

    public static void register() {
        CHERRY_BLOSSOM_CHECKED = PlacementUtils.register(
            MOD_ID + ":cherry_blossom_checked",
                Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );
        CHERRY_BLOSSOM_BEES_002 = PlacementUtils.register(
            MOD_ID + ":cherry_blossom_bees_002",
            Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM_BEES_002),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );
        CHERRY_BLOSSOM_BEES_02 = PlacementUtils.register(
            MOD_ID + ":cherry_blossom_bees_02",
            Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM_BEES_02),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );
        CHERRY_BLOSSOM_BEES_05 = PlacementUtils.register(
            MOD_ID + ":cherry_blossom_bees_05",
            Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM_BEES_05),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );

        FANCY_CHERRY_BLOSSOM_CHECKED = PlacementUtils.register(
            MOD_ID + ":fancy_cherry_blossom_checked",
            Holder.direct(ModTreeFeatures.FANCY_CHERRY_BLOSSOM),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );
        FANCY_CHERRY_BLOSSOM_BEES_05 = PlacementUtils.register(
            MOD_ID + ":fancy_cherry_blossom_bees_05",
            Holder.direct(ModTreeFeatures.FANCY_CHERRY_BLOSSOM_BEES_05),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );

        GRAND_CHERRY_BLOSSOM_CHECKED = PlacementUtils.register(
            MOD_ID + ":grand_cherry_blossom_checked",
            Holder.direct(ModTreeFeatures.GRAND_CHERRY_BLOSSOM),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );
    }
}
