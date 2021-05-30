package com.deku.cherryblossomgrotto.common.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class FallingCherryBlossomPetalType extends ParticleType<FallingCherryBlossomPetalData> {
    private static final boolean ALWAYS_SHOW = false;

    public FallingCherryBlossomPetalType() {
        super(ALWAYS_SHOW, FallingCherryBlossomPetalData.DESERIALIZER);
    }

    /**
     * Get the codec used to serialize and deserialize the particle data
     * @return
     */
    @Override
    public Codec<FallingCherryBlossomPetalData> codec() {
        return FallingCherryBlossomPetalData.CODEC;
    }
}
