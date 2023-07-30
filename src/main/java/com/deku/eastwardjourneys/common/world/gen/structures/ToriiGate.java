package com.deku.eastwardjourneys.common.world.gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class ToriiGate extends Structure {
    public static final Codec<ToriiGate> CODEC = simpleCodec(ToriiGate::new);

    public static final WeightedRandomList<MobSpawnSettings.SpawnerData> TORII_GATE_ENEMIES = WeightedRandomList.create();

    public ToriiGate(Structure.StructureSettings structureSettings) {
        super(structureSettings);
    }

    /**
     * The step during structure generation that this structure spawns in at
     *
     * @return The generation step type of this structure
     */
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    /**
     * Finds the point at which the structure will generate and starts generating its pieces if a place is find.
     *
     * @param generationContext Context of the generator for the chunk the structure is being built within
     * @return The generation stub where the structure begins to generate its first piece from
     */
    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext generationContext) {
        return onTopOfChunkCenter(generationContext, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> {
            this.generatePieces(builder, generationContext);
        });
    }

    /**
     * Generates the structure from its individual pieces.
     * Generates the structure near the middle of the chunk at whatever is the land height of that position.
     * Provides the structure with a random orientation.
     * Generates the structures bounding box.
     *
     * @param pieceBuilder The builder for all the structure's pieces
     * @param generatorContext Context for the generator for the chunk the structure is being built within
     */
    public void generatePieces(StructurePiecesBuilder pieceBuilder, Structure.GenerationContext generatorContext) {
        BlockPos chunkPos = new BlockPos(generatorContext.chunkPos().getMinBlockX(), 90, generatorContext.chunkPos().getMinBlockZ());

        NoiseColumn columnOfBlocks = generatorContext.chunkGenerator().getBaseColumn(chunkPos.getX(), chunkPos.getZ(), generatorContext.heightAccessor(), generatorContext.randomState());
        int landHeight = generatorContext.chunkGenerator().getFirstOccupiedHeight(chunkPos.getX(), chunkPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, generatorContext.heightAccessor(), generatorContext.randomState());
        BlockState topBlockState = columnOfBlocks.getBlock(landHeight);
        ToriiGatePieces.isBlockValidForStructure(topBlockState);

        BlockPos position = new BlockPos(chunkPos.getX(), landHeight + 1, chunkPos.getZ());
        Rotation rotation = Rotation.getRandom(generatorContext.random());

        ToriiGatePieces.addPieces(generatorContext.structureTemplateManager(), position, rotation, pieceBuilder, generatorContext);
    }

    /**
     * Gets the structure type of this structure
     * @return The structure type for this structure
     */
    @Override
    public StructureType<?> type() {
        return ModStructureTypeInitializer.TORII_GATE.get();
    }
}
