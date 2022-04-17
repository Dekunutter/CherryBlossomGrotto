package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.world.gen.placements.ModTreePlacements;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVegetationFeatures {
    public static ConfiguredFeature<RandomFeatureConfiguration, ?> TREES_CHERY_BLOSSOM_GROTTO;
    public static ConfiguredFeature<SimpleRandomFeatureConfiguration, ?> CHERY_BLOSSOM_GROTTO_FLOWERS;

    public static void register() {
        TREES_CHERY_BLOSSOM_GROTTO = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "trees_cherry_blossom_grotto"),
            new ConfiguredFeature<>(
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                    List.of(
                        new WeightedPlacedFeature(ModTreePlacements.GRAND_CHERRY_BLOSSOM_CHECKED, 0.01f),
                        new WeightedPlacedFeature(ModTreePlacements.FANCY_CHERRY_BLOSSOM_CHECKED, 0.1f)
                    ),
                    ModTreePlacements.CHERRY_BLOSSOM_BEES_02
                )
            )
        );

        CHERY_BLOSSOM_GROTTO_FLOWERS = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MOD_ID, "cherry_blossom_grotto_flowers"),
            new ConfiguredFeature<>(
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
            )
        );
    }
}
