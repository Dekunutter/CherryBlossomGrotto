package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlackPineButton extends ButtonBlock {
    public BlackPineButton() {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5f), ModBlockSetType.BLACK_PINE, 30, true);
    }
}
