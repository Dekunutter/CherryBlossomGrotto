package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CherryBlossomDoor extends DoorBlock
{
    public CherryBlossomDoor() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        setRegistryName("cherry_blossom_door");
    }
}
