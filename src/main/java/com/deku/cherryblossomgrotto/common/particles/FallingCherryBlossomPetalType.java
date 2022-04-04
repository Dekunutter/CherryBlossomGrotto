package com.deku.cherryblossomgrotto.common.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class FallingCherryBlossomPetalType extends ParticleType<FallingCherryBlossomPetalOptions> {
    private static final boolean ALWAYS_SHOW = false;

    public FallingCherryBlossomPetalType() {
        super(ALWAYS_SHOW, FallingCherryBlossomPetalOptions.DESERIALIZER);
    }

    /**
     * Get the codec used to serialize and deserialize the particle data
     * @return
     */
    @Override
    public Codec<FallingCherryBlossomPetalOptions> codec() {
        return FallingCherryBlossomPetalOptions.CODEC;
    }
}
