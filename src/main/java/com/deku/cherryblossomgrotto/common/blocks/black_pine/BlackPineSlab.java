package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenSlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlackPineSlab extends AbstractWoodenSlabBlock {
    public BlackPineSlab() {
        super(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD));
    }
}
