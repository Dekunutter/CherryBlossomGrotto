package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class CherryBlossomButton extends ButtonBlock {
    public CherryBlossomButton() {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5f), ModBlockSetType.CHERRY_BLOSSOM, 30, true);
    }
}
