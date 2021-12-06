package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class JunglePlanksTrapdoor extends TrapDoorBlock {
    public JunglePlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.DIRT).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("jungle_planks_trapdoor");
    }
}
