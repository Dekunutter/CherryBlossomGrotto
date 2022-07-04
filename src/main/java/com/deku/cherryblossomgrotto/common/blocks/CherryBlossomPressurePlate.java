package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomPressurePlate extends PressurePlateBlock {
    public CherryBlossomPressurePlate() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(0.5F).sound(SoundType.WOOD));
    }
}
