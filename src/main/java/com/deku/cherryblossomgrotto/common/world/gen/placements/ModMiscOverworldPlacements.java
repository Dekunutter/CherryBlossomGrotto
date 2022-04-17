package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModMiscOverworldFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModMiscOverworldPlacements {
    public static Holder<PlacedFeature> CHERRY_BLOSSOM_PETAL_TOP_LAYER;

    public static void register() {
        CHERRY_BLOSSOM_PETAL_TOP_LAYER = PlacementUtils.register(
            MOD_ID + ":cherry_blossom_petals_top_layer",
            Holder.direct(ModMiscOverworldFeatures.CHERRY_BLOSSOM_PETAL_GROUND_COVER),
            BiomeFilter.biome()
        );
    }
}
