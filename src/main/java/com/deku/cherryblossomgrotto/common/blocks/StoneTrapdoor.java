package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class StoneTrapdoor extends TrapDoorBlock {
    public StoneTrapdoor() {
        super(Properties.of(Material.STONE, MaterialColor.STONE).strength(2.0f, 6.0f).sound(SoundType.STONE));
        setRegistryName("stone_trapdoor");
    }
}
