package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModWoodType {
    public static WoodType CHERRY_BLOSSOM = new WoodType(new ResourceLocation(MOD_ID, "cherry_blossom").toString(), ModBlockSetType.CHERRY_BLOSSOM);
    public static WoodType MAPLE = new WoodType(new ResourceLocation(MOD_ID, "maple").toString(), ModBlockSetType.MAPLE);
}
