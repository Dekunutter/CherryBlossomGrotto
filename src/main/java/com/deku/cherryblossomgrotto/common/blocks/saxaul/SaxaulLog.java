package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulLog extends AbstractWoodenBlock {
    public SaxaulLog() {
        super(Properties.of().strength(2.0f).mapColor((determineMaterialColour) -> {
            return determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_LIGHT_GRAY : MapColor.COLOR_GRAY;
        }).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
        );
    }
}
