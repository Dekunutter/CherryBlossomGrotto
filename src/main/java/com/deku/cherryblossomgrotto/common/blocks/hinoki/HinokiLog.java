package com.deku.cherryblossomgrotto.common.blocks.hinoki;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class HinokiLog extends AbstractWoodenBlock {

    public HinokiLog() {
        super(Properties.of().mapColor((determineMaterialColour) -> {
            return determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_BROWN : MapColor.WOOD;
        }).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
        );
    }
}
