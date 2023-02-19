package com.deku.cherryblossomgrotto.common.blocks.maple;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class MapleButton extends ButtonBlock {
    public MapleButton() {
        super(Properties.of(Material.DECORATION).noCollission().strength(0.5f).sound(SoundType.WOOD), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }
}
