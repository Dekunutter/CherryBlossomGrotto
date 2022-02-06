package com.deku.cherryblossomgrotto.common.features;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.utils.Randomizer;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HALF_LAYERS;

public class CherryBlossomPetalCoverFeature extends Feature<NoFeatureConfig> {
    public CherryBlossomPetalCoverFeature() {
        super(NoFeatureConfig.CODEC);
    }

    /**
     * Checks each position in the current chunk and sees if we can place our top layer modifications along the surface.
     * For each position along the X-Z horizontal plane we do a full scan on the Y axis looking for cherry blossom leaves.
     * If a cherry blossom leaves block is found, we can scan down from that till we hit a subsequent block to see if we can spawn some petals.
     * The number of petal layers that we spawn is randomized.
     *
     * @param seedReader Reader for the seed that controls the generation of our world
     * @param chunkGenerator The generaotr for this chunk
     * @param random A random number generator
     * @param position The current position in the world
     * @param featureConfig Configuration class for this feature
     * @return True, indicating that we successfully ran this feature during biome generation.
     */
    @Override
    public boolean place(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig featureConfig) {
        BlockPos.Mutable mutablePosition = new BlockPos.Mutable();

        for (int chunkX = 0; chunkX < 16; chunkX++) {
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                int currentPositionX = position.getX() + chunkX;
                int currentPositionZ = position.getZ() + chunkZ;

                for (int chunkY = 256; chunkY >= 0; chunkY--) {
                    int currentPositionY = position.getY() + chunkY;

                    mutablePosition.set(currentPositionX, currentPositionY, currentPositionZ);

                    BlockState blockState = seedReader.getBlockState(mutablePosition);
                    if (blockState.getBlock().is(ModBlocks.CHERRY_LEAVES)) {
                        chunkY = spawnCherryPetals(seedReader, random, mutablePosition);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Spawns cherry blossom petal tiles across the ground below a given position.
     * Loops down through all positions from the Y axis of the given position until it hits a solid block and attempts to spawn cherry blossom leaves.
     * Even if the petals could not spawn, we return the current Y axis position since that means we hit ground anyway.
     *
     * @param seedReader Reader for the seed that is controlling this world generation
     * @param random A random number generator
     * @param position Position that we are going to start scanning for ground cover from
     * @return The position along the Y axis that we spawned some petals on
     */
    private int spawnCherryPetals(ISeedReader seedReader, Random random, BlockPos position) {
        BlockPos.Mutable belowPosition = new BlockPos.Mutable();
        BlockPos.Mutable spawningPosition = new BlockPos.Mutable();

        // TODO: Could some of this logic be combined with the checks in CherryLeavesTileEntity?
        for (int positionY = position.getY() - 1; positionY >= 0; positionY--) {
            belowPosition.set(position.getX(), positionY, position.getZ()).move(Direction.DOWN, 1);
            if (!seedReader.isEmptyBlock(belowPosition)) {
                spawningPosition.set(belowPosition).move(Direction.UP, 1);
                BlockState spawningState = seedReader.getBlockState(spawningPosition);
                if (spawningState.isAir()) {
                    if (ModBlocks.CHERRY_PETALS.defaultBlockState().canSurvive(seedReader, spawningPosition)) {
                        BlockState cherryPetalState = ModBlocks.CHERRY_PETALS.defaultBlockState();
                        cherryPetalState.setValue(HALF_LAYERS, Randomizer.getRandomNumberWithinBounds(random, 1, 4));
                        seedReader.setBlock(spawningPosition, cherryPetalState, 2);
                    }
                    return positionY;
                }
            }
        }
        return 0;
    }
}
