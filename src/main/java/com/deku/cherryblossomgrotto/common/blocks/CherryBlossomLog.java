package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;

public class CherryBlossomLog extends RotatedPillarBlock {

    public CherryBlossomLog() {
        super(AbstractBlock.Properties.of(Material.WOOD, (determineMaterialColour) ->
                determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_RED : MaterialColor.WOOD
            ).strength(2.0f).sound(SoundType.WOOD)
        );
        setRegistryName("cherry_blossom_log");
    }
}
