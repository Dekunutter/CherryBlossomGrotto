package com.deku.eastwardjourneys.common.world.gen.foliagePlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SaxaulFoliagePlacer extends FoliagePlacer {
    public static final Codec<SaxaulFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            foliagePlacerParts(instance).apply(instance, SaxaulFoliagePlacer::new)
    );

    public SaxaulFoliagePlacer(IntProvider radius, IntProvider heightOffset) {
        super(radius, heightOffset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.SAXAUL_FOLIAGE_PLACER;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelReader, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig, int trunkLength, FoliagePlacer.FoliageAttachment foliage, int foliageHeight, int foliageRadius, int offsetY) {
        boolean isDoubleTrunk = foliage.doubleTrunk();
        BlockPos foliageStartPosition = foliage.pos().above(offsetY);
        this.placeLeavesRow(levelReader, foliageSetter, random, treeConfig, foliageStartPosition, foliageRadius + foliage.radiusOffset(), -1 - foliageHeight, isDoubleTrunk);
        this.placeLeavesRow(levelReader, foliageSetter, random, treeConfig, foliageStartPosition, foliageRadius + foliage.radiusOffset(), foliageHeight, isDoubleTrunk);
        this.placeLeavesRow(levelReader, foliageSetter, random, treeConfig, foliageStartPosition, foliageRadius + foliage.radiusOffset() - 1,  foliageHeight + 1, isDoubleTrunk);
    }

    @Override
    public int foliageHeight(RandomSource p_225601_, int p_225602_, TreeConfiguration p_225603_) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int relativePositionX, int relativePositionY, int relativePositionZ, int radius, boolean hasDoubleTrunk) {
        if (relativePositionY == 1) {
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
