package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureInitializer {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> GIANT_BUDDHA = STRUCTURES.register("giant_buddha", GiantBuddha::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> TORII_GATE = STRUCTURES.register("torii_gate", ToriiGate::new);

    /**
     * Initializes all structures into the game. This includes registering them and adding them to required mappings.
     * The structure separation settings are values which represents:
     *  - The average distance between this structure and others
     *  - The minimum distance between this structure and others
     *  - The unique ID of this structure
     */
    public static void setupStructures() {
        setupMapSpacingAndLand(GIANT_BUDDHA.get(), new StructureSeparationSettings(100, 50, 565423412), true);
        setupMapSpacingAndLand(TORII_GATE.get(), new StructureSeparationSettings(10, 5, 780292865), true);
    }

    /**
     * Sets up structures by registering them, configuring their spacing, whether they should transform the land around them, and how they are affected by noise.
     * All structures that are intended to transform the land around them as they spawn into the world need to be appended to the allStructures immutable list in this function
     * BEFORE the immutable list is built.
     * Similarly, all structures need to appended to the defaultStructureSettings immutable map BEFORE the immutable map is built so that the correct structure settings are applied.
     * Then we can register all structures to the noise generator settings world gen registry.
     *
     * @param structure The structure being registered and configured
     * @param separationSettings The separation settings of this structure (average and minimum distances and a unique ID)
     * @param shouldTransformLand Whether the structure should transform the land around it
     */
    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings separationSettings, boolean shouldTransformLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (shouldTransformLand) {
            ImmutableList<Structure<?>> allStructures = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
            ForgeReflection.setStaticFinalFieldToValue(Structure.class, "NOISE_AFFECTING_FEATURES", allStructures);
        }

        ImmutableMap<Structure<?>, StructureSeparationSettings> defaultStructureSettings = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, separationSettings).build();
        ForgeReflection.setStaticFinalFieldToValue(DimensionStructuresSettings.class, "DEFAULTS", defaultStructureSettings);
        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
        });
    }
}
