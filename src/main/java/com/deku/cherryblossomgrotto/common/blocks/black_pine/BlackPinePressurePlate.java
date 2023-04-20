package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlackPinePressurePlate extends PressurePlateBlock {
    public BlackPinePressurePlate() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(0.5F).sound(SoundType.WOOD), ModBlockSetType.BLACK_PINE);
    }
}
