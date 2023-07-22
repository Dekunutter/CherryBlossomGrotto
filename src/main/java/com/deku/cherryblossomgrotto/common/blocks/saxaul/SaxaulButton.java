package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class SaxaulButton extends ButtonBlock {
    public SaxaulButton() {
        super(Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), ModBlockSetType.SAXAUL, 30, true);
    }
}
