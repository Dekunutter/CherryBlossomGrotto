package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class MaplePressurePlate extends PressurePlateBlock {
    public MaplePressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F), ModBlockSetType.MAPLE);
    }
}
