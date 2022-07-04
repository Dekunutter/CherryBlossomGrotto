package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class AcaciaPlanksTrapdoor extends TrapDoorBlock {
    public AcaciaPlanksTrapdoor() {
        super(Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0f, 3.0f).sound(SoundType.WOOD));
    }
}
