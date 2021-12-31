package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Collections;
import java.util.List;

public class ToriiGate extends Structure<NoFeatureConfig> {
    public ToriiGate() {
        super(NoFeatureConfig.CODEC);
    }

    /**
     * Getter for the start factory of this structure
     *
     * @return An instance of the start factory for this structure
     */
    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return ToriiGate.Start::new;
    }

    /**
     * The step during structure generation that this structure spawns in at
     *
     * @return The generation step type of this structure
     */
    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    /**
     * The default spawn list for entities within this structure
     *
     * @return The list of entities that can spawn in this structure
     */
    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return Collections.emptyList();
    }

    /**
     * The default spawn list for monsters within this structure
     *
     * @return The list of monsters that can spawn in this structure
     */
    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
        return Collections.emptyList();
    }

    /**
     * Performs some basic checks on the current chunk to see if the structure can be spawned here.
     * For this structure we just check that the current chunk has a height above bedrock
     *
     * @param chunkGenerator The generator generating the current chunk
     * @param biomeSource The provider of the chunk's biome
     * @param seed The seed generating the chunk
     * @param chunkRandom A shared random seed
     * @param chunkX Position of the structure within the chunk on the X axis
     * @param chunkZ Position of the structure within the chunk on the Z axis
     * @param biome The biome that the chunk is within
     * @param chunkPos The position of the chunk within the world
     * @param featureConfig Configuration of the structure
     * @return Whether the structure can spawn in this chunk
     */
    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        return landHeight > 0;
    }

    /**
     * Inner class which contains the start factory for this structure.
     * Begins the generation of the structure from individual pieces.
     */
    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seeder) {
            super(structure, chunkX, chunkZ, boundingBox, references, seeder);
        }

        /**
         * Generates the structure from its individual pieces.
         * Generates the structure near the middle of the chunk at whatever is the land height of that position.
         * Provides the structure with a random orientation.
         * Generates the structures bounding box.
         *
         * @param registries The dynamic registries for this structure
         * @param chunkGenerator The generator for the chunk the structure is being built within
         * @param templateManager Manager for loading templates
         * @param chunkX Position of the structure within the chunk on the X axis
         * @param chunkZ Position of the structure within the chunk on the Z axis
         * @param biome The biome that the chunk is within
         * @param config Configuration of the structure
         */
        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);

            BlockPos position = new BlockPos(chunkX * 16, landHeight, chunkZ * 16);
            Rotation rotation = Rotation.getRandom(this.random);
            ToriiGatePieces.addPieces(templateManager, position, rotation, this.pieces, this.random);
            this.calculateBoundingBox();
        }
    }
}
