package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.Main;
import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.deku.cherryblossomgrotto.common.world.gen.biomes.ModBiomeInitializer;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;

import java.util.HashMap;
import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModConfiguredStructureInitializer {
    public static StructureFeature<?, ?> CONFIGURED_GIANT_BUDDHA = ModStructureInitializer.GIANT_BUDDHA.get().configured(NoFeatureConfig.INSTANCE);
    public static StructureFeature<?, ?> CONFIGURED_TORII_GATE = ModStructureInitializer.TORII_GATE.get().configured(NoFeatureConfig.INSTANCE);
    public static StructureFeature<VillageConfig, ? extends Structure<VillageConfig>> VILLAGE_CHERRY_BLOSSOM_GROTTO = Structure.VILLAGE.configured(new VillageConfig(() -> {
        return CherryBlossomGrottoVillagePools.START;
    }, 6));

    /**
     * Registers all structures as configured structure features by using the configured structure feature world gen registry.
     * Also ensures that the structures can be added to flat world generators.
     */
    public static void registerConfiguredStructures() {
        CherryBlossomGrottoVillagePools.bootstrap();
        JigsawPatternRegistry.bootstrap();

        Map<RegistryKey<Biome>, VillagerType> villagerBiomes =  (Map<RegistryKey<Biome>, VillagerType>) ForgeReflection.getObfuscatedPrivatizedFieldValue(VillagerType.class, "field_221180_h");
        Map<RegistryKey<Biome>, VillagerType> newVillagerBiomes = new HashMap<>(villagerBiomes);
        newVillagerBiomes.put(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO, VillagerType.PLAINS);
        ForgeReflection.setObfuscatedStaticFinalFieldToValue(VillagerType.class, "field_221180_h", newVillagerBiomes);

        Registry<StructureFeature<? ,?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(MOD_ID, "configured_giant_buddha"), CONFIGURED_GIANT_BUDDHA);
        Registry.register(registry, new ResourceLocation(MOD_ID, "configured_torii_gate"), CONFIGURED_TORII_GATE);
        Registry.register(registry, new ResourceLocation(MOD_ID, "village_cherry_blossom_grotto"), VILLAGE_CHERRY_BLOSSOM_GROTTO);

        addStructuresToFlatGeneration();
    }

    /**
     *  Allows modded structures to generate in flat world generation by adding them to the structure features of the flat generation settings class.
     */
    private static void addStructuresToFlatGeneration() {
        Map<Structure<?>, StructureFeature<?, ?>> originalValues = (HashMap) ForgeReflection.getObfuscatedPrivatizedFieldValue(FlatGenerationSettings.class, "field_202247_j");
        Map<Structure<?>, StructureFeature<?, ?>> newValues = new HashMap<>(originalValues);
        newValues.put(ModStructureInitializer.GIANT_BUDDHA.get(), CONFIGURED_GIANT_BUDDHA);
        newValues.put(ModStructureInitializer.TORII_GATE.get(), CONFIGURED_TORII_GATE);
        newValues.put(Structure.VILLAGE, VILLAGE_CHERRY_BLOSSOM_GROTTO);
        ForgeReflection.setObfuscatedStaticFinalFieldToValue(FlatGenerationSettings.class, "field_202247_j", newValues);
    }
}
