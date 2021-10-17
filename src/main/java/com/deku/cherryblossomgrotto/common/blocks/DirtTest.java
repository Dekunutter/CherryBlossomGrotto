package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class DirtTest extends Block {
    public DirtTest() {
        super(AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5f).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation("minecraft", "dirt"));
    }
}
