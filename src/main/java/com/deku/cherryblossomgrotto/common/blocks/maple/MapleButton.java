package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class MapleButton extends ButtonBlock {
    public MapleButton() {
        super(Properties.of().noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY), ModBlockSetType.MAPLE, 30, true);
    }
}
