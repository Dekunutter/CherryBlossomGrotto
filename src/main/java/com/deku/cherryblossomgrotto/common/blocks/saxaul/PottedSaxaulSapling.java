package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.PushReaction;

public class PottedSaxaulSapling extends FlowerPotBlock {
    public PottedSaxaulSapling() {
        super(ModBlocks.SAXAUL_SAPLING, Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:saxaul_sapling"), () -> this);
    }
}
