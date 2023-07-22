package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModWoodType {
    public static WoodType MAPLE = new WoodType(new ResourceLocation(MOD_ID, "maple").toString(), ModBlockSetType.MAPLE);
    public static WoodType BLACK_PINE = new WoodType(new ResourceLocation(MOD_ID, "black_pine").toString(), ModBlockSetType.BLACK_PINE);
    public static WoodType HINOKI = new WoodType(new ResourceLocation(MOD_ID, "hinoki").toString(), ModBlockSetType.HINOKI);
    public static WoodType WATER_FIR = new WoodType(new ResourceLocation(MOD_ID, "water_fir").toString(), ModBlockSetType.WATER_FIR);
    public static WoodType SAXAUL = new WoodType(new ResourceLocation(MOD_ID, "saxaul").toString(), ModBlockSetType.SAXAUL);
}
