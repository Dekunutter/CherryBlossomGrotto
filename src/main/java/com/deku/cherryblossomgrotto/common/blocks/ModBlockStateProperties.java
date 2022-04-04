package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties {
    public static final BooleanProperty CHERRY_BLOSSOM_PETAL_COVERED = BooleanProperty.create("cherry_blossom_petal_covered");

    public static final IntegerProperty HALF_LAYERS = IntegerProperty.create("half_layers", 1, 4);

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
}
