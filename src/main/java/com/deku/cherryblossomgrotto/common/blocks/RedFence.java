package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class RedFence extends FenceBlock {
    public RedFence() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.COLOR_RED).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
