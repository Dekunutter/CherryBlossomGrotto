package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CherryBlossomTrapDoor extends TrapDoorBlock {
    public CherryBlossomTrapDoor() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        setRegistryName("cherry_blossom_trapdoor");
    }
}
