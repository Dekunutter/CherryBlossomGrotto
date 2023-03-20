package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBlockSetType {
    public static BlockSetType CHERRY_BLOSSOM = new BlockSetType(new ResourceLocation(MOD_ID, "cherry_blossom").toString(), SoundType.CHERRY_WOOD, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundEvents.CHERRY_WOOD_DOOR_OPEN, SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE, SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON);
    public static BlockSetType MAPLE = new BlockSetType(new ResourceLocation(MOD_ID, "maple").toString());
}
