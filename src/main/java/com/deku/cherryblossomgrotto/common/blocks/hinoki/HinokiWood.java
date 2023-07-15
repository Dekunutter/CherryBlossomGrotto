package com.deku.cherryblossomgrotto.common.blocks.hinoki;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class HinokiWood extends AbstractWoodenBlock {
    public HinokiWood() {
        super(Properties.of().strength(2.0f).mapColor(MapColor.WOOD).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
