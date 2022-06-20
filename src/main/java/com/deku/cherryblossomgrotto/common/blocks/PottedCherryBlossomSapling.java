package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class PottedCherryBlossomSapling extends FlowerPotBlock {
    public PottedCherryBlossomSapling() {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(), () -> ModBlocks.CHERRY_SAPLING.delegate.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion());
        setRegistryName("potted_cherry_blossom_sapling");
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:cherry_blossom_sapling"), () -> this);
    }
}
