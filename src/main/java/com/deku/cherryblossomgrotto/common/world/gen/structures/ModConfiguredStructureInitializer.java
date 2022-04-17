package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;

import java.util.HashMap;
import java.util.Map;

public class ModConfiguredStructureInitializer {
    // TODO: May need to move this to ensure it happens AFTER initialization of the above original structure features
    public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_GIANT_BUDDHA = registerConfigured(
        ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
            new ResourceLocation("giant_buddha")
        ),
        ModStructureInitializer.GIANT_BUDDHA.get().configured(
            NoneFeatureConfiguration.INSTANCE,
            ModBiomeTags.HAS_GIANT_BUDDHA,
            false,
            Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, GiantBuddha.GIANT_BUDDHA_ENEMIES))
        )
    );
    public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_TORII_GATE = registerConfigured(
        ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
            new ResourceLocation("torii_gate")
        ),
        ModStructureInitializer.TORII_GATE.get().configured(
            NoneFeatureConfiguration.INSTANCE,
            ModBiomeTags.HAS_TORII_GATE,
            false,
            Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, ToriiGate.TORII_GATE_ENEMIES))
        )
    );
    public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_CHERRY_BLOSSOM_GROTTO_VILLAGE = registerConfigured(
        ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
            new ResourceLocation("village_cherry_blossom_grotto")
        ),
        StructureFeature.VILLAGE.configured(
            new JigsawConfiguration(CherryBlossomGrottoVillagePools.START, 6),
            ModBiomeTags.HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE,
            true
        )
    );

    public static void bootstrap() {

    }

    /**
     * Registers all structures as configured structure features by using the configured structure feature world gen registry.
     */
    public static void registerConfiguredStructures() {
        CherryBlossomGrottoVillagePools.bootstrap();

        Map<ResourceKey<Biome>, VillagerType> villagerBiomes =  (Map<ResourceKey<Biome>, VillagerType>) ForgeReflection.getObfuscatedPrivatizedFieldValue(VillagerType.class, "f_35827_");
        Map<ResourceKey<Biome>, VillagerType> newVillagerBiomes = new HashMap<>(villagerBiomes);
        newVillagerBiomes.put(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO, VillagerType.PLAINS);
        ForgeReflection.setObfuscatedStaticFinalFieldToValue(VillagerType.class, "f_35827_", newVillagerBiomes);
    }

    /**
     * Register a configured structure feature with the built-in registry for these objects
     *
     * @param resourceKey The resource key for this configured structure feature
     * @param configuredStructure The configured structure feature we are reigstering
     * @param <FC> The feature configuration used by the configured structure feature
     * @param <F> The structure feature from which this configured structure feature is configured from
     * @return A holder containing the configured structure feature which was just registered
     */
    private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> Holder<ConfiguredStructureFeature<?, ?>> registerConfigured(ResourceKey<ConfiguredStructureFeature<?, ?>> resourceKey, ConfiguredStructureFeature<FC, F> configuredStructure) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceKey, configuredStructure);
    }
}
