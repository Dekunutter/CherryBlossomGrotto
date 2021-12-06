package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class AcaciaPlanksTrapdoor extends TrapDoorBlock {
    public AcaciaPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("acacia_planks_trapdoor");
    }
}
