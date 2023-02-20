package com.deku.cherryblossomgrotto.common.blocks.maple;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class MapleFence extends FenceBlock {
    public MapleFence() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD));
    }
}
