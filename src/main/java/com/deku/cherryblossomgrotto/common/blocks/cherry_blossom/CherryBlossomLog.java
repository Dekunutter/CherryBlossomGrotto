package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomLog extends AbstractWoodenBlock {
    public CherryBlossomLog() {
        super(BlockBehaviour.Properties.of(Material.WOOD, (determineMaterialColour) ->
                determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_RED : MaterialColor.WOOD
            ).strength(2.0f).sound(SoundType.WOOD)
        );
    }
}
