package com.deku.eastwardjourneys.common.world.gen.trunkPlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import static com.deku.eastwardjourneys.common.world.gen.trunkPlacers.ModTrunkPlacerTypes.BLACK_PINE_TREE_TRUNK_PLACER;

public class BlackPineTrunkPlacer extends AbstractBlackPineTrunkPlacer {
    public static final Codec<BlackPineTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            trunkPlacerParts(instance).apply(instance, BlackPineTrunkPlacer::new)
    );

    public BlackPineTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return BLACK_PINE_TREE_TRUNK_PLACER.get();
    }
}
