package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;

public class CherryBlossomSapling extends SaplingBlock {
    public CherryBlossomSapling() {
        super(new OakTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
        setRegistryName("cherry_blossom_sapling");
    }
}
