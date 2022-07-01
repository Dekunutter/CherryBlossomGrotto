package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVillagePlacements {
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_TREE_VILLAGE;
    public static Holder<PlacedFeature> FLOWER_FOREST_VILLAGE;

    /**
     * Registers village placements into the game via the placed features registry.
     *
     * Village placements are just features intended specifically for use in villages as decorations
     */
    public static void register() {
        CHERRY_BLOSSOM_TREE_VILLAGE = PlacementUtils.register(
            MOD_ID + ":cherry_blossom",
            Holder.direct(ModTreeFeatures.CHERRY_BLOSSOM),
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING)
        );

        FLOWER_FOREST_VILLAGE = PlacementUtils.register(
            MOD_ID + ":flower_forest",
            VegetationFeatures.FLOWER_FLOWER_FOREST
        );
    }
}
