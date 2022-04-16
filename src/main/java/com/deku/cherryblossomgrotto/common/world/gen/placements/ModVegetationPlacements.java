package com.deku.cherryblossomgrotto.common.world.gen.placements;

import com.deku.cherryblossomgrotto.common.features.ModVegetationFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVegetationPlacements {
    public static final Holder<PlacedFeature> TREES_CHERRY_BLOSSOM_GROTTO = PlacementUtils.register(MOD_ID + ":trees_cherry_blossom_grotto", ModVegetationFeatures.TREES_CHERY_BLOSSOM_GROTTO, VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.F, 1)));
    public static final Holder<PlacedFeature> CHERRY_BLOSSOM_GROTTO_FLOWERS = PlacementUtils.register(MOD_ID + ":cherry_blossom_grotto_flowers", ModVegetationFeatures.CHERY_BLOSSOM_GROTTO_FLOWERS, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)), BiomeFilter.biome());
}
