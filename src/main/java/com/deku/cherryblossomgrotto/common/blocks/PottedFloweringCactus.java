package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.PushReaction;

public class PottedFloweringCactus extends FlowerPotBlock {
    public PottedFloweringCactus() {
        super(ModBlocks.FLOWERING_CACTUS, Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("cherryblossomgrotto:flowering_cactus"), () -> this);
    }
}
