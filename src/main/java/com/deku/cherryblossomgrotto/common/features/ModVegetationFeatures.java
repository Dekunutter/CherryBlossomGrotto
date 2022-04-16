package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.world.gen.placements.ModTreePlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVegetationFeatures {
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_CHERY_BLOSSOM_GROTTO = FeatureUtils.register(
        MOD_ID + ":trees_cherry_blossom_grotto",
        Feature.RANDOM_SELECTOR,
        new RandomFeatureConfiguration(
            List.of(
                new WeightedPlacedFeature(ModTreePlacements.GRAND_CHERRY_BLOSSOM_CHECKED, 0.01f),
                new WeightedPlacedFeature(ModTreePlacements.FANCY_CHERRY_BLOSSOM_CHECKED, 0.1f)
            ),
            ModTreePlacements.CHERRY_BLOSSOM_BEES_02
        )
    );
    public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CHERY_BLOSSOM_GROTTO_FLOWERS = FeatureUtils.register(
        MOD_ID + ":cherry_blossom_grotto_flowers",
        Feature.SIMPLE_RANDOM_SELECTOR,
        new SimpleRandomFeatureConfiguration(
            HolderSet.direct(
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.NO_BONEMEAL_FLOWER,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY)))
                ),
                PlacementUtils.inlinePlaced(
                    Feature.RANDOM_PATCH,
                    FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM)))
                )
            )
        )
    );
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CHERRY_BLOSSOM_PETAL_GROUND_COVER = FeatureUtils.register(
            MOD_ID + ":cherry_blossom_petal_ground_cover",
            ModFeatures.CHERRY_BLOSSOM_GROUND_COVER
    );
}
