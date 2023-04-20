package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.AbstractPlanksBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlackPinePlanks extends AbstractPlanksBlock {
    public BlackPinePlanks() {
        super(Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0f, 3.0f).sound(SoundType.WOOD));
    }
}
