package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModOreFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON_SPARSE = registerOreFeatureKey("ore_iron_sparse");

    /**
     * Registers a resource key for the given ore feature name
     *
     * @param featureName Name of the feature we want to create a resource key for
     * @return The resource key created for the given feature
     */
    public static ResourceKey<ConfiguredFeature<?, ?>> registerOreFeatureKey(String featureName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MOD_ID, featureName));
    }

    /**
     * Creates an ore configuration for sparse iron
     *
     * @return The ore configuration for sparse iron
     */
    private static OreConfiguration createSparseIronOreConfiguration() {
        List<OreConfiguration.TargetBlockState> targets = List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), Blocks.IRON_ORE.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), Blocks.DEEPSLATE_IRON_ORE.defaultBlockState()));
        return new OreConfiguration(targets, 2, 0.2F);
    }

    /**
     * Registers ore features using the bootstrap context
     *
     * @param context The bootstrap context
     */
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(ORE_IRON_SPARSE, new ConfiguredFeature<>(Feature.ORE, createSparseIronOreConfiguration()));
    }
}
