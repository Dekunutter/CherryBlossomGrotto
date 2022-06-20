package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomDoor extends DoorBlock
{
    public CherryBlossomDoor() {
        super(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(3.0f).sound(SoundType.WOOD).noOcclusion());
        setRegistryName("cherry_blossom_door");
    }
}
