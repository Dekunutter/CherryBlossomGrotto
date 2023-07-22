package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class StrippedSaxaulWood extends AbstractWoodenBlock {
    public StrippedSaxaulWood() {
        super(Properties.of().strength(2.0f).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
