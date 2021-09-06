package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;

public class ModBlockStateProperties {
    public static final BooleanProperty CHERRY_BLOSSOM_PETAL_COVERED = BooleanProperty.create("cherry_blossom_petal_covered");

    public static final IntegerProperty HALF_LAYERS = IntegerProperty.create("half_layers", 1, 4);
}
