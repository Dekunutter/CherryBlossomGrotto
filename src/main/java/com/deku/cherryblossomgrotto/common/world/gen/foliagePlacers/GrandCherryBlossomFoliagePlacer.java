package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import com.deku.cherryblossomgrotto.common.utils.Randomizer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class GrandCherryBlossomFoliagePlacer extends FoliagePlacer {
    public static final Codec<GrandCherryBlossomFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            foliagePlacerParts(instance).apply(instance, GrandCherryBlossomFoliagePlacer::new)
    );

    public GrandCherryBlossomFoliagePlacer(IntProvider radius, IntProvider heightOffset) {
        super(radius, heightOffset);
    }

    /**
     * Fetches the associated foliage placer type for this foliage placer
     *
     * @return The foliage placer type
     */
    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.GRAND_CHERRY_TREE_FOLIAGE_PLACER;
    }

    /**
     * Places the foliage for this tree into the world.
     * This will generate three rows of foliage where applicable, starting with the largest row, which is flush with the top of the trunk.
     * The following rows will overwrite each other with a low radius as the top of the tree if foliage height is low.
     *
     * @param levelReader Reader for the level the foliage is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param trunkLength Length of the current tree's trunk
     * @param foliage Foliage settings for this foliage placer
     * @param foliageHeight Height of the foliage to be created at this foliage point
     * @param foliageRadius The radius of the foliage
     * @param offsetY The offset on the Y-axis to start the placement position
     */
    @Override
    protected void createFoliage(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, int trunkLength, FoliagePlacer.FoliageAttachment foliage, int foliageHeight, int foliageRadius, int offsetY) {
        if (foliage.doubleTrunk()) {
            // Places the base row which is a cross of leaves and at the top level of logs
            this.placeLeavesRow(levelReader, blockConsumer, random, treeConfig, foliage.pos(), foliageRadius - 2, -1, true);

            generateCanopy(levelReader, blockConsumer, random, treeConfig, foliage, foliageHeight, foliageRadius);
        } else {
            foliageRadius -= 3;

            // top off with a small amount of leaves
            this.placeLeavesRow(levelReader, blockConsumer, random, treeConfig, foliage.pos(), 1, 1, false);

            // Places the base row which is a cross of leaves and at the top level of logs
            this.placeLeavesRow(levelReader, blockConsumer, random, treeConfig, foliage.pos(), foliageRadius, 0, false);

            // bottom out with a single block of leaves to cover the source log
            this.placeLeavesRow(levelReader, blockConsumer, random, treeConfig, foliage.pos(), 0, -1, false);
        }
    }

    /**
     * Places foliage in a typical canopy shape by looping through all height levels for the leave generation and making individual rows one after the other with a degrading radius value.
     *
     * @param levelReader Reader for the level the foliage is being generated in
     * @param blockConsumer Consumer for block position and state
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param foliage Foliage settings for this foliage placer
     * @param foliageHeight Height of the foliage to be created at this foliage point
     * @param foliageRadius The radius of the foliage
     */
    private void generateCanopy(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> blockConsumer, RandomSource random, TreeConfiguration treeConfig, FoliagePlacer.FoliageAttachment foliage, int foliageHeight, int foliageRadius) {
        for (int positionY = foliageHeight; positionY > -1; positionY--) {
            int radius = Math.max(foliageRadius + foliage.radiusOffset() - positionY, 0);
            this.placeLeavesRow(levelReader, blockConsumer, random, treeConfig, foliage.pos(), radius, positionY, foliage.doubleTrunk());
        }
    }

    /**
     * Determines the height of the foliage for this tree.
     * If this is increased then the number of rows per foliage spawn point will increase
     *
     * @param random A random number generator
     * @param trunkHeight Height of the trunk spawning this foliage
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return The height of the foliage for this tree
     */
    @Override
    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration treeConfig) {
        return Randomizer.getRandomNumberWithinBounds(random, 3, 4);
    }

    /**
     * Determines if foliage placement should skip the current location.
     * If the current horizontal positions being placed are a corner, then we will skip placement since the foliage would generate very square-like.
     * Corner checks happen when the horizontal axis exceed or match the radius size when the radius is above 0.
     * For leaves generating on the spawning row we don't want to place them if the horizontal axis are trying to place anywhere beyond directly against the trunk so we limit the radius to 1 on this row.
     *
     * @param random A random number generator
     * @param relativePositionX Our relative position on the X axis within the radius of leaves being placed for the current row
     * @param relativePositionY The relative position on the Y axis for the foliage we want to generate
     * @param relativePositionZ Our relative position on the Z axis within the radius of leaves being placed for the current row
     * @param radius Radius of the current row of leaves being placed
     * @param hasDoubleTrunk Whether the tree has a double-radius trunk or not
     * @return Whether the placer should skip the current location
     */
    @Override
    protected boolean shouldSkipLocation(RandomSource random, int relativePositionX, int relativePositionY, int relativePositionZ, int radius, boolean hasDoubleTrunk) {
        if (relativePositionY == -1) {
            return (relativePositionX > 1 || relativePositionZ > 1) && relativePositionX != 0 && relativePositionZ != 0;
        } else {
            int relativePosition = (relativePositionX * relativePositionX) + (relativePositionZ * relativePositionZ);
            int radiusSquared = radius * radius;
            if (relativePosition == radiusSquared) {
                return Randomizer.getRandomNumberWithinBounds(random, 0, 20) >= 18;
            } else if (relativePosition > radiusSquared) {
                return Randomizer.getRandomNumberWithinBounds(random, 0, 20) >= 3;
            } else {
                return false;
            }
        }
    }
}
