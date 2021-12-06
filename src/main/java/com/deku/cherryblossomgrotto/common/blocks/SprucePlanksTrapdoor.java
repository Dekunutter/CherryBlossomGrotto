package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class SprucePlanksTrapdoor extends TrapDoorBlock {
    public SprucePlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("spruce_planks_trapdoor");
    }
}
