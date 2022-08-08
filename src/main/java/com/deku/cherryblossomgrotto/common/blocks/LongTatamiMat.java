package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class LongTatamiMat extends LongCarpetBlock {
    public LongTatamiMat() {
        super(Properties.of(Material.BAMBOO, MaterialColor.COLOR_BROWN).strength(0.2F).sound(SoundType.BAMBOO));
    }
}
