package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class DarkOakPlanksTrapdoor extends TrapDoorBlock {
    public DarkOakPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("dark_oak_planks_trapdoor");
    }
}
