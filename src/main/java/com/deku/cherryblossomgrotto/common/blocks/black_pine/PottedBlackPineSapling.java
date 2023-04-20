package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.Material;

public class PottedBlackPineSapling extends FlowerPotBlock {
    public PottedBlackPineSapling() {
        super(ModBlocks.BLACK_PINE_SAPLING, Properties.of(Material.DECORATION).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:black_pine_sapling"), () -> this);
    }
}
