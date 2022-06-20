package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class ModConfiguredFeatures {
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE_BEES_002;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE_BEES_02;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> CHERRY_TREE_BEES_05;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> FANCY_CHERRY_TREE;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> FANCY_CHERRY_TREE_BEES_05;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> GRAND_CHERRY_TREE;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CHERRY_PETAL_COVER;

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> IRON_ORE_SPARSE;

    public static ConfiguredFeature<?, ?> CHERRY_TREE_FOREST;
    public static ConfiguredFeature<?, ?> CHERRY_TREE_FOREST_FLOWERS;
}