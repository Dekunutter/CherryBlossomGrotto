package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModWoodType {
    public static WoodType CHERRY_BLOSSOM = WoodType.create(new ResourceLocation(MOD_ID, "cherry_blossom").toString());
    public static WoodType MAPLE = WoodType.create(new ResourceLocation(MOD_ID, "maple").toString());
}
