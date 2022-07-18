package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import static net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType.BENDING_TRUNK_PLACER;

public class FancyCherryBlossomTrunkPlacer extends AbstractCherryBlossomTrunkPlacer {
    public static final Codec<FancyCherryBlossomTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, FancyCherryBlossomTrunkPlacer::new)
    );

    public FancyCherryBlossomTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
        setBranchingType(AbstractCherryBlossomTrunkPlacer.BranchingType.CANOPY);
        setTrunkCurvingOffset(6);
        setTrunkCurvingLengthMax(5);
    }

    /**
     * Fetches the associated trunk placer type for this trunk placer
     *
     * @return The trunk placer type
     */
    @Override
    protected TrunkPlacerType<?> type() {
        return BENDING_TRUNK_PLACER;
    }
}
