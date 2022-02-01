package com.deku.cherryblossomgrotto.common.world.gen.foliagePlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class CherryBlossomFoliagePlacer extends FoliagePlacer {
    public static final Codec<CherryBlossomFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
        foliagePlacerParts(instance).apply(instance, CherryBlossomFoliagePlacer::new)
    );

    public CherryBlossomFoliagePlacer(FeatureSpread radius, FeatureSpread heightOffset) {
        super(radius, heightOffset);
    }

    /**
     * Fetches the associated foliage placer type for this foliage placer
     *
     * @return The foliage placer type
     */
    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.CHERRY_TREE_FOLIAGE_PLACER;
    }

    /**
     * Places the foliage for this tree into the world.
     * This will generate three rows of foliage where applicable, starting with the largest row, which is flush with the top of the trunk.
     * The following rows will overwrite each other with a low radius as the top of the tree if foliage height is low.
     *
     * @param worldGenReader Instance of the world generator
     * @param random A random number generator
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @param trunkLength Length of the current tree's trunk
     * @param foliage Foliage settings for this foliage placer
     * @param foliageHeight Height of the foliage to be created at this foliage point
     * @param foliageRadius The radius of the foliage
     * @param placedBlockPositions The position of all blocks placed into the world by this foliage generation
     * @param offsetY The offset on the Y-axis to start the placement position
     * @param boundingBox Bounding limitations of the generator
     */
    @Override
    protected void createFoliage(IWorldGenerationReader worldGenReader, Random random, BaseTreeFeatureConfig treeConfig, int trunkLength, Foliage foliage, int foliageHeight, int foliageRadius, Set<BlockPos> placedBlockPositions, int offsetY, MutableBoundingBox boundingBox) {
        boolean isDoubleTrunk = foliage.doubleTrunk();
        BlockPos foliageStartPosition = foliage.foliagePos().above(offsetY);
        this.placeLeavesRow(worldGenReader, random, treeConfig, foliageStartPosition, foliageRadius + foliage.radiusOffset(), placedBlockPositions, -1 - foliageHeight, isDoubleTrunk, boundingBox);
        this.placeLeavesRow(worldGenReader, random, treeConfig, foliageStartPosition, foliageRadius - 1, placedBlockPositions, -foliageHeight, isDoubleTrunk, boundingBox);

        if (random.nextInt(2) == 1) {
            this.placeLeavesRow(worldGenReader, random, treeConfig, foliageStartPosition, foliageRadius + foliage.radiusOffset() - 1, placedBlockPositions, -2 - foliageHeight, isDoubleTrunk, boundingBox);
        }
    }

    /**
     * Determines the height of the foliage for this tree.
     * If this is increased then the number of rows per foliage spawn point will increase
     *
     * @param random A random number generator
     * @param trunkLength length of the trunk this foliage is spawning on
     * @param treeConfig Configuration class for getting the state of the placed blocks
     * @return The height of the foliage for this tree
     */
    @Override
    public int foliageHeight(Random random, int trunkLength, BaseTreeFeatureConfig treeConfig) {
        return 0;
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
    protected boolean shouldSkipLocation(Random random, int relativePositionX, int relativePositionY, int relativePositionZ, int radius, boolean hasDoubleTrunk) {
        if (relativePositionY == 0) {
            if (relativePositionX >= 1 && relativePositionZ >= 1) {
                return true;
            }
            return (relativePositionX > 1 || relativePositionZ > 1) && relativePositionX != 0 && relativePositionZ != 0;
        } else {
            if (relativePositionX == radius) {
                return relativePositionZ >= (radius - 1);
            } else if (relativePositionZ == radius) {
                return relativePositionX >= (radius - 1);
            }
            return false;
        }
    }
}
