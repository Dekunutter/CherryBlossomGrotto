package com.deku.eastwardjourneys.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.material.PushReaction;

public class PottedEnokiMushroom extends FlowerPotBlock {
    public PottedEnokiMushroom() {
        super(ModBlocks.ENOKI_MUSHROOM, Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation("eastwardjourneys:enoki_mushroom"), () -> this);
    }
}
