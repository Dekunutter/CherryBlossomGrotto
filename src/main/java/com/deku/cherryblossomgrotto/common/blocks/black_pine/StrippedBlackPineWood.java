package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class StrippedBlackPineWood extends AbstractWoodenBlock {
    public StrippedBlackPineWood() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0f).sound(SoundType.WOOD));
    }
}
