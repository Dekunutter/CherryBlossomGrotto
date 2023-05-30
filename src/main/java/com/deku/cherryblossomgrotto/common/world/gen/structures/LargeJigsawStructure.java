package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Optional;
import java.util.function.Function;

// NOTE: Without a custom system, 128 blocks is going to be about the limit to not interfere with generated chunks. Therefore, I should increase max depth but reduce max distance from center and go with a smaller wall
// Maybe there'd be some smart way to start another wall straight after the last completed but I'm not sure I want to go down that road right now. Sounds like a bit much
public class LargeJigsawStructure extends JigsawStructure {
    public static final Codec<LargeJigsawStructure> CODEC = RecordCodecBuilder.<LargeJigsawStructure>mapCodec((instance) -> {
        return instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((structure) -> {
            return structure.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> {
            return structure.startJigsawName;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((structure) -> {
            return structure.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter((structure) -> {
            return structure.startHeight;
        }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((structure) -> {
            return structure.useExpansionHack;
        }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((structure) -> {
            return structure.projectStartToHeightmap;
        }), Codec.intRange(1, 160).fieldOf("max_distance_from_center").forGetter((structure) -> {
            return structure.maxDistanceFromCenter;
        })).apply(instance, LargeJigsawStructure::new);
    }).flatXmap(verifyRange(), verifyRange()).codec();

    public LargeJigsawStructure(StructureSettings settings, Holder<StructureTemplatePool> templatePool, Optional<ResourceLocation> resourceLocation, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(settings, templatePool, resourceLocation, maxDepth, startHeight, useExpansionHack, projectStartToHeightmap, maxDistanceFromCenter);
        this.startPool = templatePool;
        this.startJigsawName = resourceLocation;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    public LargeJigsawStructure(StructureSettings settings, Holder<StructureTemplatePool> templatePool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Heightmap.Types projectStartToHeightmap) {
        super(settings, templatePool, maxDepth, startHeight, useExpansionHack, projectStartToHeightmap);
    }

    public LargeJigsawStructure(StructureSettings settings, Holder<StructureTemplatePool> templatePool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack) {
        super(settings, templatePool, maxDepth, startHeight, useExpansionHack);
    }

    /**
     * The type of structure this is
     *
     * @return The structure type
     */
    @Override
    public StructureType<?> type() {
        return ModStructureTypeInitializer.LARGE_JIGSAW.get();
    }

    /**
     * Verifies that the structure config sets parameters that are within the allowed range.
     *
     * NOTE: Copy of vanilla function from JigsawStructure
     *
     * @return Whether the structure config is saet to a maximum size under 160 once factoring in beardification
     */
    private static Function<LargeJigsawStructure, DataResult<LargeJigsawStructure>> verifyRange() {
        return (instance) -> {
            byte b0;
            switch (instance.terrainAdaptation()) {
                case NONE:
                    b0 = 0;
                    break;
                case BURY:
                case BEARD_THIN:
                case BEARD_BOX:
                    b0 = 12;
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            int i = b0;
            return instance.maxDistanceFromCenter + i > 160 ? DataResult.error(() -> {
                return "Structure size including terrain adaptation must not exceed 160";
            }) : DataResult.success(instance);
        };
    }

    /**
     * Finds the generation point for t his structure and begins the placement of the individual pieces
     *
     * @param generationContext Context for the generation
     * @return The generation stub where the structure begins to generate it's first piece from
     */
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext generationContext) {
        ChunkPos chunkpos = generationContext.chunkPos();
        int i = startHeight.sample(generationContext.random(), new WorldGenerationContext(generationContext.chunkGenerator(), generationContext.heightAccessor()));
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), i, chunkpos.getMinBlockZ());
        return JigsawPlacement.addPieces(generationContext, startPool, startJigsawName, maxDepth, blockpos, useExpansionHack, projectStartToHeightmap, maxDistanceFromCenter);
    }
}
