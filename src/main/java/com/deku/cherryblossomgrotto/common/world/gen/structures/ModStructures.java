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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;

import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructures {
    public static Holder<Structure> GIANT_BUDDHA;
    public static Holder<Structure> TORII_GATE;
    public static Holder<Structure> CHERRY_BLOSSOM_GROTTO_VILLAGE;

    public static Holder<Structure> RUINED_TORII_PORTAL;

    public static void register() {
        GIANT_BUDDHA = registerStructure(
            ResourceKey.create(Registry.STRUCTURE_REGISTRY,
                new ResourceLocation(MOD_ID, "giant_buddha")
            ),
            new GiantBuddha(
                new Structure.StructureSettings(
                    BuiltinRegistries.BIOME.getOrCreateTag(ModBiomeTags.HAS_GIANT_BUDDHA),
                    Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, GiantBuddha.GIANT_BUDDHA_ENEMIES)),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.BURY
                )
            )
        );

        TORII_GATE = registerStructure(
            ResourceKey.create(Registry.STRUCTURE_REGISTRY,
                new ResourceLocation(MOD_ID,"torii_gate")
            ),
            new ToriiGate(
                new Structure.StructureSettings(
                    BuiltinRegistries.BIOME.getOrCreateTag(ModBiomeTags.HAS_TORII_GATE),
                    Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, ToriiGate.TORII_GATE_ENEMIES)),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE
                )
            )
        );

        // NOTE: Keeping this in code as an option since I may want to convert the structure to a jigsaw structure in the future,
        //  but for now I'm keeping it as a multi-piece structure instead of a jigsaw structure so that I have more control over the pre-spawning checks
        /*TORII_GATE_JIGSAW = registerStructure(
            ResourceKey.create(Registry.STRUCTURE_REGISTRY,
                new ResourceLocation(MOD_ID, "torii_gate")
            ),
            new JigsawStructure(
                new Structure.StructureSettings(
                    BuiltinRegistries.BIOME.getOrCreateTag(ModBiomeTags.HAS_TORII_GATE),
                    Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, ToriiGate.TORII_GATE_ENEMIES)),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE
                ),
                ToriiGatePools.START,
                2,
                ConstantHeight.of(VerticalAnchor.absolute(1)),
                true,
                Heightmap.Types.WORLD_SURFACE_WG
            )
        );*/

        registerModdedVillageStructure();

        // TODO: Pretty sure I got to plug this into vanilla villages instead of it coming up as its own structure in location commands but something to look into later
        //  Should see if this TODO is even relevant for 1.19.0 though
        CHERRY_BLOSSOM_GROTTO_VILLAGE = registerStructure(
            ResourceKey.create(Registry.STRUCTURE_REGISTRY,
                new ResourceLocation(MOD_ID, "village_cherry_blossom_grotto")
            ),
            new JigsawStructure(
                new Structure.StructureSettings(
                    BuiltinRegistries.BIOME.getOrCreateTag(ModBiomeTags.HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE),
                    Map.of(),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.BEARD_THIN
                ),
                CherryBlossomGrottoVillagePools.START,
                6,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                true,
                Heightmap.Types.WORLD_SURFACE_WG
            )
        );

        RUINED_TORII_PORTAL = registerStructure(
            ResourceKey.create(Registry.STRUCTURE_REGISTRY,
                new ResourceLocation(MOD_ID, "ruined_torii_portal")
            ),
            new RuinedToriiPortal(
                new Structure.StructureSettings(
                    BuiltinRegistries.BIOME.getOrCreateTag(ModBiomeTags.HAS_RUINED_TORII_PORTAL),
                    Map.of(),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE
                ),
                new RuinedToriiPortal.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5F, 0.0F, false, false, false, false, 1.0F)
            )
        );
    }

    /**
     * Registers the structure representing the custom village by adding it to the mappings of village types supported by vanilla minecraft
     */
    private static void registerModdedVillageStructure() {
        CherryBlossomGrottoVillagePools.bootstrap();

        VillagerType.BY_BIOME.put(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO, ModVillagerTypes.CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE);
    }

    /**
     * Register a structure with the built-in registry for these objects
     *
     * @param resourceKey The resource key for this structure
     * @param structure The structure we are reigstering
     * @return A holder containing the structure which was just registered
     */
    private static Holder<Structure> registerStructure(ResourceKey<Structure> resourceKey, Structure structure) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, resourceKey, structure);
    }
}
