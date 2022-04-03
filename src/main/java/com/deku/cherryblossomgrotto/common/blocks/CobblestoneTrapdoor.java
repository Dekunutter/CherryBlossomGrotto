package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CobblestoneTrapdoor extends TrapDoorBlock {
    public CobblestoneTrapdoor() {
        super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.5f, 6.0f).sound(SoundType.STONE));
        setRegistryName("cobblestone_trapdoor");
    }
}
