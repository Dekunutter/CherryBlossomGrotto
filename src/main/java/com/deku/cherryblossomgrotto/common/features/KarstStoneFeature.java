package com.deku.cherryblossomgrotto.common.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import javax.annotation.Nullable;

// NOTE: Though this feature would work better as a biome with custom noise settings, overriding those in Minecraft 1.19.X is going to cause compatibility issues with other mods since there's no easy way to update that configuration currently
// TODO: Need to add structures to the biome and see how they act when they spawn in
//  AAAAALSO, would love to make a flooded version of this biome. One that, like a swamp, spawns a lot of shallow water on the surface layer rather than trees (maybe if I increase spring water spawn rates with a custom feature configuration but otherwise, swamp-style surfaces would require an update to the dimension noise settings :\
public class KarstStoneFeature extends Feature<KarstStoneFeature.Configuration> {
    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.CHEST, Blocks.SPAWNER);

    public KarstStoneFeature(Codec<KarstStoneFeature.Configuration> codec) {
        super(codec);
    }

    /**
     * Places the feature into the world
     *
     * @param featureConfig The configuration for the current feature
     * @return Whether the feature was placed into the world or not
     */
    @Override
    public boolean place(FeaturePlaceContext<Configuration> featureConfig) {
        int seaLevel = featureConfig.chunkGenerator().getSeaLevel();
        BlockPos originPosition = featureConfig.origin();
        WorldGenLevel level = featureConfig.level();
        RandomSource random = featureConfig.random();
        // TODO: An additional length so that these features spawn more rectangular than square? Would need to make sure its randomly oriented along the XZ plane though. Even oriented at angles if possible
        KarstStoneFeature.Configuration configuration = featureConfig.config();
        if (!canPlaceAt(level, seaLevel, originPosition.mutable())) {
            return false;
        } else {
            BlockState stoneToPlace = configuration.stone().getState(random, originPosition);
            int reach = configuration.reach().sample(random);
            int maxHeight = configuration.height().sample(random);

            boolean isComplex = random.nextFloat() < 0.9F;
            int randomBounds = Math.min(maxHeight, isComplex ? 5 : 8);
            int columnPositions = isComplex ? 50 : 15;
            boolean wasColumnPlaced = false;

            for(BlockPos randomPosition : BlockPos.randomBetweenClosed(random, columnPositions, originPosition.getX() - randomBounds, originPosition.getY(), originPosition.getZ() - randomBounds, originPosition.getX() + randomBounds, originPosition.getY(), originPosition.getZ() + randomBounds)) {
                // we get a max height specific to the current position within the bounds which is slightly shorter based on the distance between the current position and the origin of the feature
                int maxHeightAtPosition = maxHeight - randomPosition.distManhattan(originPosition);
                if (maxHeightAtPosition >= 0) {
                    wasColumnPlaced |= this.placeColumn(level, seaLevel, randomPosition, maxHeightAtPosition, reach, stoneToPlace);
                }
            }

            return wasColumnPlaced;
        }
    }

    /**
     * Places a block of columns into the world.
     *
     * @param level The level the columns are being placed into
     * @param seaLevel The sea level of the current chunk
     * @param position The position we are placing the columns at
     * @param maxHeightAtPosition The maximum height we want the columns to be at the current position
     * @param reach The size of the block of columns
     * @return Whether a block of columns was placed into the world or not.
     */
    private boolean placeColumn(LevelAccessor level, int seaLevel, BlockPos position, int maxHeightAtPosition, int reach, BlockState stoneToPlace) {
        boolean columnPlaced = false;

        // For each position in the AABB between the current position and the min/max reach of the feature along the XZ plane
        //  Scan downwards to find a suitable spawning point for a column of stone if we are starting from a non-solid block, otherwise scan upwards to find the surface
        //  If valid, position is returned and blockstate is updated, then we make our way upwards from that starting position to build a column
        //  Repeat from another position within the bounds till we have a large square of columns
        for(BlockPos positionInBounds : BlockPos.betweenClosed(position.getX() - reach, position.getY(), position.getZ() - reach, position.getX() + reach, position.getY(), position.getZ() + reach)) {
            int distance = positionInBounds.distManhattan(position);
            BlockPos startingPosition = isAirOrWaterBelowSeaLevel(level, seaLevel, positionInBounds) ? findSurface(level, seaLevel, positionInBounds.mutable(), distance) : findAir(level, positionInBounds.mutable(), distance);
            if (startingPosition != null) {
                int height = maxHeightAtPosition - distance / 2;

                int columnHeight = 0;
                for(BlockPos.MutableBlockPos columnPosition = startingPosition.mutable(); height >= 0; --height) {
                    if (isAirOrWaterBelowSeaLevel(level, seaLevel, columnPosition)) {
                        this.setBlock(level, columnPosition, stoneToPlace);
                        columnPosition.move(Direction.UP);
                        columnPlaced = true;
                        columnHeight++;
                    } else {
                        if (!level.getBlockState(columnPosition).is(Blocks.STONE)) {
                            break;
                        }

                        columnPosition.move(Direction.UP);
                    }
                }

                // Descend the column underground till stone is found so that it blends more naturally with the world instead of just spawning on grass
                BlockPos.MutableBlockPos undergroundColumnPosition = startingPosition.mutable();
                undergroundColumnPosition.move(Direction.DOWN);
                while(!level.getBlockState(undergroundColumnPosition).is(BlockTags.BASE_STONE_OVERWORLD) && !level.getBlockState(undergroundColumnPosition).isAir()) {
                    setBlock(level, undergroundColumnPosition, stoneToPlace);
                    undergroundColumnPosition.move(Direction.DOWN);
                }

                if (columnPlaced) {
                    RandomSource random = level.getRandom();

                    // Random chance that tops of columns are replaced with a block of grass
                    if (random.nextInt(5) == 0) {
                        BlockPos topColumnPosition = new BlockPos(new BlockPos(startingPosition.getX(), startingPosition.getY() + (columnHeight - 1), startingPosition.getZ()));
                        BlockState aboveColumnState = level.getBlockState(topColumnPosition);
                        if(aboveColumnState.is(Blocks.STONE)) {
                            setBlock(level, topColumnPosition, Blocks.GRASS_BLOCK.defaultBlockState());
                        }
                    }


                    // Random chance that increases with column height to add some moss carpetting on top of the rocks
                    if (random.nextInt(3) == 0) {
                        BlockPos aboveColumnPosition = new BlockPos(new BlockPos(startingPosition.getX(), startingPosition.getY() + columnHeight, startingPosition.getZ()));
                        BlockState aboveColumnState = level.getBlockState(aboveColumnPosition);
                        if(aboveColumnState.isAir()) {
                            setBlock(level, aboveColumnPosition, Blocks.MOSS_CARPET.defaultBlockState());
                        }
                    }
                }

                // TODO: Add vines along some walls for colour variety
            }
        }

        return columnPlaced;
    }

    /**
     * Finds the surface block be scanning downwards from the current position or until a maximum distance threshold has been passed
     *
     * @param level The level we are scanning within
     * @param seaLevel The sea level of the current chunk
     * @param position The position we will start scanning from
     * @param distance The distance we want to scan for
     * @return The position of the surface, or null if the surface could not be found
     */
    @Nullable
    private static BlockPos findSurface(LevelAccessor level, int seaLevel, BlockPos.MutableBlockPos position, int distance) {
        while(position.getY() > level.getMinBuildHeight() + 1 && distance > 0) {
            --distance;
            if (canPlaceAt(level, seaLevel, position)) {
                return position;
            }

            position.move(Direction.DOWN);
        }

        return null;
    }

    /**
     * Checks if the feature can be placed at the given position.
     * The position needs to be an air block or a water block below sea level, and spawning on a sturdy full-faced block below it.
     *
     * @param level The level within which we are attempting to place the feature
     * @param seaLevel The sea level of the current chunk
     * @param position The position we want to place the feature at
     * @return Whether we can place the feature here or not
     */
    private static boolean canPlaceAt(LevelAccessor level, int seaLevel, BlockPos.MutableBlockPos position) {
        if (!isAirOrWaterBelowSeaLevel(level, seaLevel, position)) {
            return false;
        } else {
            BlockState belowBlockState = level.getBlockState(position.move(Direction.DOWN));
            boolean isSpawnSturdy = belowBlockState.isFaceSturdy(level, position, Direction.UP);
            // Move mutable position back to original place on the Y axis
            position.move(Direction.UP);
            return !belowBlockState.isAir() && isSpawnSturdy && !CANNOT_PLACE_ON.contains(belowBlockState.getBlock());
        }
    }

    /**
     * Scans upwards from the given position till an air block is found or a maximum distance threshold has been crossed.
     * If the feature cannot spawn on a particular list of blocks then null is returned
     *
     * @param level The level we are scanning for air in
     * @param position The position we want to start scanning for air from
     * @param distance The distance within which we want to scan for an air block
     * @return The position we find an air block in, or null if no air block could be found
     */
    @Nullable
    private static BlockPos findAir(LevelAccessor level, BlockPos.MutableBlockPos position, int distance) {
        while(position.getY() < level.getMaxBuildHeight() && distance > 0) {
            --distance;
            BlockState blockstate = level.getBlockState(position);
            if (CANNOT_PLACE_ON.contains(blockstate.getBlock())) {
                return null;
            }

            if (blockstate.isAir()) {
                return position;
            }

            position.move(Direction.UP);
        }

        return null;
    }

    /**
     * Checks if the given position is an air or water block.
     * The water block should be at a position equal to or below sea level so we know its approaching our surface plane
     *
     * @param level Level in which we are checking the blockstate in
     * @param seaLevel The sea level for the current chunk
     * @param position The position at which we are checking blockstates
     * @return Whether the block at the given position is air or sea-level water
     */
    private static boolean isAirOrWaterBelowSeaLevel(LevelAccessor level, int seaLevel, BlockPos position) {
        BlockState blockState = level.getBlockState(position);
        return blockState.isAir() || blockState.is(Blocks.WATER) && position.getY() <= seaLevel;
    }

    /**
     * The feature configuration for a karst stone feature.
     * Holds the block states for the stone we are using, as well as the reach and height of the columns
     *
     * @param stone
     * @param reach
     * @param height
     */
    public static record Configuration(BlockStateProvider stone, IntProvider reach, IntProvider height) implements FeatureConfiguration {
        public static final Codec<KarstStoneFeature.Configuration> CODEC = RecordCodecBuilder.create((codec) -> {
            return codec.group(
                BlockStateProvider.CODEC.fieldOf("stone").forGetter(KarstStoneFeature.Configuration::stone),
                IntProvider.codec(1, 2).fieldOf("reach").forGetter((v) -> { return v.reach; }),
                IntProvider.codec(8, 25).fieldOf("height").forGetter((v) -> { return v.height; })
            ).apply(codec, KarstStoneFeature.Configuration::new);
        });
    }
}
