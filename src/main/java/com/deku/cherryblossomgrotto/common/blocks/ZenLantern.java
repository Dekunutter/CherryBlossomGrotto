package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.particles.ParticleTypes;

public class ZenLantern extends AbstractZenLantern {
    private static int LIGHT_LEVEL = 15;

    public ZenLantern() {
        super(LIGHT_LEVEL, ParticleTypes.FLAME);
        setRegistryName("zen_lantern");
    }
}
