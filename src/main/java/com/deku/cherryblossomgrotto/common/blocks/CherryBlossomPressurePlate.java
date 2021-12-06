package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CherryBlossomPressurePlate extends PressurePlateBlock {
    public CherryBlossomPressurePlate() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(0.5F).sound(SoundType.WOOD));
        setRegistryName("cherry_blossom_pressure_plate");
    }
}
