package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.WoodType;
import net.minecraft.util.ResourceLocation;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModWoodType {
    public static WoodType CHERRY_BLOSSOM = WoodType.create(new ResourceLocation(MOD_ID, "cherry_blossom").toString());
}
