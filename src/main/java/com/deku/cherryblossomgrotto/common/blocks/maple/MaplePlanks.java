package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.AbstractPlanksBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MaplePlanks extends AbstractPlanksBlock {
    public MaplePlanks() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.PODZOL).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava());
    }
}
