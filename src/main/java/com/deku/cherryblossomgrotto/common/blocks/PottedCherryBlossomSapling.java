package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class PottedCherryBlossomSapling extends FlowerPotBlock {
    public PottedCherryBlossomSapling() {
        super(ModBlocks.CHERRY_SAPLING, BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:cherry_blossom_sapling"), () -> this);
    }
}
