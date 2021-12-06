package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class SmoothStoneTrapdoor extends TrapDoorBlock {
    public SmoothStoneTrapdoor() {
        super(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE).strength(2.0f, 6.0f).sound(SoundType.STONE));
        setRegistryName("smooth_stone_trapdoor");
    }
}
