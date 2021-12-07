package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class PottedCherryBlossomSapling extends FlowerPotBlock {
    public PottedCherryBlossomSapling() {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> ModBlocks.CHERRY_SAPLING.delegate.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion());
        setRegistryName("potted_cherry_blossom_sapling");
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:cherry_blossom_sapling"), () -> this);
    }
}
