package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureInitializer {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);

    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> GIANT_BUDDHA = STRUCTURES.register("giant_buddha", GiantBuddha::new);
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> TORII_GATE = STRUCTURES.register("torii_gate", ToriiGate::new);

    // TODO: May need to move this to ensure it happens AFTER initialization of the above structures
    public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_GIANT_BUDDHA = registerConfigured(
        ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
            new ResourceLocation("giant_buddha")
        ),
        GIANT_BUDDHA.get().configured(
            NoneFeatureConfiguration.INSTANCE,
            TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("has_structure/giant_buddha")),
            false,
            Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, GiantBuddha.GIANT_BUDDHA_ENEMIES))
        )
    );
    public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_TORII_GATE = registerConfigured(
        ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY,
            new ResourceLocation("torii_gate")
        ),
        TORII_GATE.get().configured(
            NoneFeatureConfiguration.INSTANCE,
            TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("has_structure/torii_gate")),
            false,
            Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, ToriiGate.TORII_GATE_ENEMIES))
        )
    );

    public static final Holder<StructureSet> GIANT_BUDDHAS = registerStructureSet(
        ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation("giant_buddhas")),
        new StructureSet(
            CONFIGURED_GIANT_BUDDHA,
            new RandomSpreadStructurePlacement(100, 50, RandomSpreadType.LINEAR, 565423412)
        )
    );
    public static final Holder<StructureSet> TORII_GATES = registerStructureSet(
        ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation("torii_gates")),
        new StructureSet(
            CONFIGURED_TORII_GATE,
            new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, 780292865)
        )
    );

    private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> Holder<ConfiguredStructureFeature<?, ?>> registerConfigured(ResourceKey<ConfiguredStructureFeature<?, ?>> resourceKey, ConfiguredStructureFeature<FC, F> configuredStructure) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceKey, configuredStructure);
    }

    private static Holder<StructureSet> registerStructureSet(ResourceKey<StructureSet> resourceKey, StructureSet structureSet) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, resourceKey, structureSet);
    }
}
