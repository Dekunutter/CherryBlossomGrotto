package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class DarkOakPlanksTrapdoor extends TrapDoorBlock {
    public DarkOakPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0f, 3.0f).sound(SoundType.WOOD));
    }
}
