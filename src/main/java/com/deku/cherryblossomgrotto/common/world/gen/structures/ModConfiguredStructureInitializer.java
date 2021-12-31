package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.HashMap;
import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModConfiguredStructureInitializer {
    public static StructureFeature<?, ?> CONFIGURED_GIANT_BUDDHA = ModStructureInitializer.GIANT_BUDDHA.get().configured(NoFeatureConfig.INSTANCE);
    public static StructureFeature<?, ?> CONFIGURED_TORII_GATE = ModStructureInitializer.TORII_GATE.get().configured(NoFeatureConfig.INSTANCE);

    /**
     * Registers all structures as configured structure features by using the configured structure feature world gen registry.
     * Also ensures that the structures can be added to flat world generators.
     */
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<? ,?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(MOD_ID, "configured_giant_buddha"), CONFIGURED_GIANT_BUDDHA);
        Registry.register(registry, new ResourceLocation(MOD_ID, "configured_torii_gate"), CONFIGURED_TORII_GATE);

        addStructuresToFlatGeneration();
    }

    /**
     *  Allows modded structures to generate in flat world generation by adding them to the structure features of the flat generation settings class.
     */
    private static void addStructuresToFlatGeneration() {
        Map<Structure<?>, StructureFeature<?, ?>> originalValues = (HashMap) ForgeReflection.getPrivatizedFieldValue(FlatGenerationSettings.class, "STRUCTURE_FEATURES");
        Map<Structure<?>, StructureFeature<?, ?>> newValues = new HashMap<>(originalValues);
        newValues.put(ModStructureInitializer.GIANT_BUDDHA.get(), CONFIGURED_GIANT_BUDDHA);
        newValues.put(ModStructureInitializer.TORII_GATE.get(), CONFIGURED_TORII_GATE);
        ForgeReflection.setStaticFinalFieldToValue(FlatGenerationSettings.class, "STRUCTURE_FEATURES", newValues);
    }
}
