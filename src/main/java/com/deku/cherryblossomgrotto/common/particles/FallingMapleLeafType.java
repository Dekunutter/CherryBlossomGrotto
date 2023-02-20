package com.deku.cherryblossomgrotto.common.particles;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class FallingMapleLeafType extends ParticleType<FallingMapleLeafOptions> {
    private static final boolean ALWAYS_SHOW = false;

    public FallingMapleLeafType() {
        super(ALWAYS_SHOW, FallingMapleLeafOptions.DESERIALIZER);
    }

    /**
     * Get the codec used to serialize and deserialize the particle data
     * @return
     */
    @Override
    public Codec<FallingMapleLeafOptions> codec() {
        return FallingMapleLeafOptions.CODEC;
    }
}
