package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModOreFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModOrePlacements {
    public static final Holder<PlacedFeature> ORE_IRON_SPARSE = PlacementUtils.register(
        MOD_ID + ":ore_iron_sparse",
        ModOreFeatures.ORE_IRON_SPARSE,
        List.of(
            CountPlacement.of(10),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)),
            BiomeFilter.biome()
        )
    );
}
