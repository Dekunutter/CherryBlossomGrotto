package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModOreFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModOrePlacements {
    public static Holder<PlacedFeature> ORE_IRON_SPARSE;
    public static Holder<PlacedFeature> ORE_IRON_SPARSE_UPPER;

    public static void register() {
        ORE_IRON_SPARSE = PlacementUtils.register(
            MOD_ID + ":ore_iron_sparse",
            Holder.direct(ModOreFeatures.ORE_IRON_SPARSE),
            List.of(
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)),
                BiomeFilter.biome()
            )
        );

        ORE_IRON_SPARSE_UPPER = PlacementUtils.register(
            MOD_ID + ":ore_iron_sparse_upper",
            Holder.direct(ModOreFeatures.ORE_IRON_SPARSE),
            List.of(
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)),
                BiomeFilter.biome()
            )
        );
    }
}
