package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class WaterFirButton extends ButtonBlock {
    public WaterFirButton() {
        super(Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), ModBlockSetType.WATER_FIR, 30, true);
    }
}
