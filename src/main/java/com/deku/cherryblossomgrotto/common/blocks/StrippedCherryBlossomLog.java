package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class StrippedCherryBlossomLog extends RotatedPillarBlock {
    public StrippedCherryBlossomLog() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(2.0f).sound(SoundType.WOOD) );
        setRegistryName("stripped_cherry_blossom_log");
    }
}
