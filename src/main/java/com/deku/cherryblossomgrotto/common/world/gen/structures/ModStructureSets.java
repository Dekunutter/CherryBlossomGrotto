package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

public class ModStructureSets {
    public static final Holder<StructureSet> GIANT_BUDDHAS = registerStructureSet(
        ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation("giant_buddhas")),
        new StructureSet(
            ModConfiguredStructureInitializer.CONFIGURED_GIANT_BUDDHA,
            new RandomSpreadStructurePlacement(100, 50, RandomSpreadType.LINEAR, 565423412)
        )
    );
    public static final Holder<StructureSet> TORII_GATES = registerStructureSet(
        ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation("torii_gates")),
        new StructureSet(
            ModConfiguredStructureInitializer.CONFIGURED_TORII_GATE,
            new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, 780292865)
        )
    );
    // Overriding the structure set for the base-games villages
    public static final Holder<StructureSet> CHERRY_BLOSSOM_GROTTO_VILLAGES = registerStructureSet(
        BuiltinStructureSets.VILLAGES,
        new StructureSet(
            List.of(
                StructureSet.entry(StructureFeatures.VILLAGE_PLAINS),
                StructureSet.entry(StructureFeatures.VILLAGE_DESERT),
                StructureSet.entry(StructureFeatures.VILLAGE_SAVANNA),
                StructureSet.entry(StructureFeatures.VILLAGE_SNOWY),
                StructureSet.entry(StructureFeatures.VILLAGE_TAIGA),
                StructureSet.entry(ModConfiguredStructureInitializer.CONFIGURED_CHERRY_BLOSSOM_GROTTO_VILLAGE)
            ),
            new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 10387312)
        )
    );

    /**
     * Register a structure set with the built-in registry for these objects
     *
     * @param resourceKey The resource key for this configured structure set
     * @param structureSet The structure set we are registering
     * @return A holder containing the structure set which was just registered
     */
    private static Holder<StructureSet> registerStructureSet(ResourceKey<StructureSet> resourceKey, StructureSet structureSet) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, resourceKey, structureSet);
    }
}
