package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlackPineLog extends AbstractWoodenBlock {
    public BlackPineLog() {
        super(Properties.of(Material.WOOD, (determineMaterialColour) ->
                determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.WOOD : MaterialColor.PODZOL
            ).strength(2.0f).sound(SoundType.WOOD)
        );
    }
}
