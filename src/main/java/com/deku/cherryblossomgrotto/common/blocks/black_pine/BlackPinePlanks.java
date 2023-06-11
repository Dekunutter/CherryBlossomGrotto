package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.AbstractPlanksBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BlackPinePlanks extends AbstractPlanksBlock {
    public BlackPinePlanks() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.PODZOL).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava());
    }
}
