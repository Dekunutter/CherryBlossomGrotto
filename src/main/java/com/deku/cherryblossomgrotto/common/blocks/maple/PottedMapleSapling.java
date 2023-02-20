package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.Material;

public class PottedMapleSapling extends FlowerPotBlock {
    public PottedMapleSapling() {
        super(ModBlocks.MAPLE_SAPLING, Properties.of(Material.DECORATION).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:maple_sapling"), () -> this);
    }
}
