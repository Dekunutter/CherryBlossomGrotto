package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class ToriiGate extends StructureFeature<NoneFeatureConfiguration> {
    public static final WeightedRandomList<MobSpawnSettings.SpawnerData> TORII_GATE_ENEMIES = WeightedRandomList.create();

    public ToriiGate() {
        super(NoneFeatureConfiguration.CODEC, PieceGeneratorSupplier.simple(ToriiGate::checkLocation, ToriiGate::generatePieces));
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
     * Performs some basic checks on the current chunk to see if the structure can be spawned here.
     * For this structure we just check that the current chunk has a height above bedrock
     *
     * @param generatorSupplier The generator supplying the current chunk
     * @return Whether the structure can spawn in this chunk
     */
    protected static <C extends FeatureConfiguration> boolean checkLocation(PieceGeneratorSupplier.Context<C> generatorSupplier) {
        BlockPos centerOfChunk = new BlockPos(generatorSupplier.chunkPos().getMinBlockX() * 16, 90, generatorSupplier.chunkPos().getMinBlockZ() * 16);

        int landHeight = generatorSupplier.chunkGenerator().getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, generatorSupplier.heightAccessor());
        return landHeight >= generatorSupplier.chunkGenerator().getSeaLevel();
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
    public static void generatePieces(StructurePiecesBuilder pieceBuilder, PieceGenerator.Context<NoneFeatureConfiguration> generatorContext) {
        BlockPos centerOfChunk = new BlockPos(generatorContext.chunkPos().getMinBlockX() * 16, 90, generatorContext.chunkPos().getMinBlockZ() * 16);
        int landHeight = generatorContext.chunkGenerator().getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, generatorContext.heightAccessor());

        BlockPos position = new BlockPos(generatorContext.chunkPos().getMinBlockX() * 16, landHeight, generatorContext.chunkPos().getMinBlockZ() * 16);
        Rotation rotation = Rotation.getRandom(generatorContext.random());
        ToriiGatePieces.addPieces(generatorContext.structureManager(), position, rotation, pieceBuilder, generatorContext.random());
    }
}
