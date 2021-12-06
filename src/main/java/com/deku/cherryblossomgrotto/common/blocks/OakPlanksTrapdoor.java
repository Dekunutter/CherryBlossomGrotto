package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class OakPlanksTrapdoor extends TrapDoorBlock {
    public OakPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("oak_planks_trapdoor");
    }
}
