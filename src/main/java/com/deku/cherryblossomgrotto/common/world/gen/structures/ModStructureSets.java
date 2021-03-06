package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureSets {
    public static Holder<StructureSet> GIANT_BUDDHAS;
    public static Holder<StructureSet> TORII_GATES;

    // Overriding the structure set for the base-games villages
    public static Holder<StructureSet> CHERRY_BLOSSOM_GROTTO_VILLAGES;

    public static Holder<StructureSet> RUINED_TORII_PORTALS;

    /**
     * Registers all structure sets into the game via the structure set registry.
     *
     * Structure sets are collections of structures, grouped by a commonality (e.g: all villages being in the village structure set)
     */
    public static void register() {
        GIANT_BUDDHAS = registerStructureSet(
            ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(MOD_ID, "giant_buddhas")),
            new StructureSet(
                ModStructures.GIANT_BUDDHA,
                new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 565423412)
            )
        );

        TORII_GATES = registerStructureSet(
            ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(MOD_ID,"torii_gates")),
            new StructureSet(
                ModStructures.TORII_GATE,
                new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 780292865)
            )
        );

        // TODO: This may not be compatible with other mods if its overwriting other village structure sets
        CHERRY_BLOSSOM_GROTTO_VILLAGES = registerStructureSet(
            BuiltinStructureSets.VILLAGES,
            new StructureSet(
                List.of(
                    StructureSet.entry(Structures.VILLAGE_PLAINS),
                    StructureSet.entry(Structures.VILLAGE_DESERT),
                    StructureSet.entry(Structures.VILLAGE_SAVANNA),
                    StructureSet.entry(Structures.VILLAGE_SNOWY),
                    StructureSet.entry(Structures.VILLAGE_TAIGA),
                    StructureSet.entry(ModStructures.CHERRY_BLOSSOM_GROTTO_VILLAGE)
                ),
                new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 10387312)
            )
        );

        RUINED_TORII_PORTALS = registerStructureSet(
            ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(MOD_ID,"ruined_torii_portals")),
            new StructureSet(
                ModStructures.RUINED_TORII_PORTAL,
                new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 106574789)
            )
        );
    }

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
