package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBlockSetType {
    public static BlockSetType MAPLE = new BlockSetType(new ResourceLocation(MOD_ID, "maple").toString());
    public static BlockSetType BLACK_PINE = new BlockSetType(new ResourceLocation(MOD_ID, "black_pine").toString());
}
