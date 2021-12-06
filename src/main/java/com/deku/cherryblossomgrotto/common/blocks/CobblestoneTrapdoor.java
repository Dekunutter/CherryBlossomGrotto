package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CobblestoneTrapdoor extends TrapDoorBlock {
    public CobblestoneTrapdoor() {
        super(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE));
        setRegistryName("cobblestone_trapdoor");
    }
}
