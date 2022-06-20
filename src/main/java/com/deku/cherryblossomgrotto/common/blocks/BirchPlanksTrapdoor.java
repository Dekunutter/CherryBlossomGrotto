package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BirchPlanksTrapdoor extends TrapDoorBlock {
    public BirchPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0f, 3.0f).sound(SoundType.WOOD));
        setRegistryName("birch_planks_trapdoor");
    }
}
