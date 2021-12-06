package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CherryBlossomPlanksTrapdoor extends TrapDoorBlock {
    public CherryBlossomPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("cherry_blossom_planks_trapdoor");
    }
}
