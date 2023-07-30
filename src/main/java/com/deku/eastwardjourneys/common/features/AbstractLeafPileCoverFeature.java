package com.deku.eastwardjourneys.common.features;

import com.deku.eastwardjourneys.common.utils.Randomizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionDefaults;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static com.deku.eastwardjourneys.common.blocks.ModBlockStateProperties.HALF_LAYERS;

public class AbstractLeafPileCoverFeature extends Feature<NoneFeatureConfiguration> {
    private final Block leaves;
    private final Block leafPile;

    public AbstractLeafPileCoverFeature(Block leaves, Block leafPile) {
        super(NoneFeatureConfiguration.CODEC);
        this.leaves = leaves;
        this.leafPile = leafPile;
    }

    /**
     * Checks each position in the current chunk and sees if we can place our top layer modifications along the surface.
     * For each position along the X-Z horizontal plane we do a full scan on the Y axis looking for leaves.
     * If the right leaves block is found, we can scan down from that till we hit a subsequent block to see if we can spawn some petals.
     * The number of petal layers that we spawn is randomized.
     *
     * @param featureConfig Configuration class for this feature
     * @return True, indicating that we successfully ran this feature during biome generation.
     */
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
        BlockPos.MutableBlockPos mutablePosition = new BlockPos.MutableBlockPos();

        BlockPos position = featureConfig.origin();
        for (int chunkX = 0; chunkX < 16; chunkX++) {
            for (int chunkZ = 0; chunkZ < 16; chunkZ++) {
                int currentPositionX = position.getX() + chunkX;
                int currentPositionZ = position.getZ() + chunkZ;

                for (int chunkY = 256; chunkY >= DimensionDefaults.OVERWORLD_MIN_Y; chunkY--) {
                    int currentPositionY = position.getY() + chunkY;

                    mutablePosition.set(currentPositionX, currentPositionY, currentPositionZ);

                    BlockState blockState = featureConfig.level().getBlockState(mutablePosition);
                    if (blockState.getBlock() == leaves) {
                        chunkY = spawnLeafPile(featureConfig.level(), featureConfig.random(), mutablePosition);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Spawns leaf pile blocks across the ground below a given position.
     * Loops down through all positions from the Y axis of the given position until it hits a solid block and attempts to spawn leaves.
     * Even if the petals could not spawn, we return the current Y axis position since that means we hit ground anyway.
     *
     * @param levelGenerationReader Reader for the generation logic that is controlling this world's formation
     * @param random A random number generator
     * @param position Position that we are going to start scanning for ground cover from
     * @return The position along the Y axis that we spawned some petals on
     */
    private int spawnLeafPile(WorldGenLevel levelGenerationReader, RandomSource random, BlockPos position) {
        BlockPos.MutableBlockPos belowPosition = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos spawningPosition = new BlockPos.MutableBlockPos();

        // TODO: Could some of this logic be combined with the checks in ParticleLeavesBlockEntity?
        for (int positionY = position.getY() - 1; positionY >= DimensionDefaults.OVERWORLD_MIN_Y; positionY--) {
            belowPosition.set(position.getX(), positionY, position.getZ()).move(Direction.DOWN, 1);
            if (!levelGenerationReader.isEmptyBlock(belowPosition)) {
                spawningPosition.set(belowPosition).move(Direction.UP, 1);
                BlockState spawningState = levelGenerationReader.getBlockState(spawningPosition);
                if (spawningState.isAir()) {
                    if (levelGenerationReader.getBlockState(belowPosition).is(leafPile)) {
                        continue;
                    }
                    if (leafPile.defaultBlockState().canSurvive(levelGenerationReader, spawningPosition)) {
                        BlockState pileState = leafPile.defaultBlockState();
                        pileState.setValue(HALF_LAYERS, Randomizer.getRandomNumberWithinBounds(random, 1, 4));
                        levelGenerationReader.setBlock(spawningPosition, pileState, 2);
                    }
                    return positionY;
                }
            }
        }
        return 0;
    }
}
