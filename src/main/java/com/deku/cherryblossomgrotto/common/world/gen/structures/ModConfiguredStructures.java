package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.entity.npc.ModVillagerTypes;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;

import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModConfiguredStructures {
    public static Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_GIANT_BUDDHA;
    public static Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_TORII_GATE;
    public static Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_CHERRY_BLOSSOM_GROTTO_VILLAGE;

    public static void register() {
        CONFIGURED_GIANT_BUDDHA = registerConfigured(
            ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
                new ResourceLocation(MOD_ID, "giant_buddha")
            ),
            ModStructureInitializer.GIANT_BUDDHA.get().configured(
                NoneFeatureConfiguration.INSTANCE,
                ModBiomeTags.HAS_GIANT_BUDDHA,
                false,
                Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, GiantBuddha.GIANT_BUDDHA_ENEMIES))
            )
        );

        CONFIGURED_TORII_GATE = registerConfigured(
            ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
                new ResourceLocation(MOD_ID,"torii_gate")
            ),
            ModStructureInitializer.TORII_GATE.get().configured(
                NoneFeatureConfiguration.INSTANCE,
                ModBiomeTags.HAS_TORII_GATE,
                false,
                Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, ToriiGate.TORII_GATE_ENEMIES))
            )
        );

        registerModdedVillageStructure();

        // TODO: Pretty sure I got to plug this into vanilla villages instead of it coming up as its own structure in location commands but something to look into later
        CONFIGURED_CHERRY_BLOSSOM_GROTTO_VILLAGE = registerConfigured(
            ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
                new ResourceLocation(MOD_ID, "village_cherry_blossom_grotto")
            ),
            StructureFeature.VILLAGE.configured(
                new JigsawConfiguration(CherryBlossomGrottoVillagePools.START, 6),
                ModBiomeTags.HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE,
                true
            )
        );
    }

    /**
     * Registers the configured structure feature representing the custom village by adding it to the mappings of village types supported by vanilla minecraft
     */
    private static void registerModdedVillageStructure() {
        CherryBlossomGrottoVillagePools.bootstrap();

        VillagerType.BY_BIOME.put(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO, ModVillagerTypes.CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE);
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
