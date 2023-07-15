package com.deku.cherryblossomgrotto.common.blocks.hinoki;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class HinokiButton extends ButtonBlock {
    public HinokiButton() {
        super(Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), ModBlockSetType.HINOKI, 30, true);
    }
}
