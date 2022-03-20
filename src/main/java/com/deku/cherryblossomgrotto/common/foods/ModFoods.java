package com.deku.cherryblossomgrotto.common.foods;

import net.minecraft.item.Food;

public class ModFoods {
    public static final Food RICE = new Food.Builder().nutrition(2).saturationMod(0.3F).build();
    public static final Food ONIGIRI = new Food.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final Food KOI = new Food.Builder().nutrition(2).saturationMod(0.1F).build();
    public static final Food COOKED_KOI = new Food.Builder().nutrition(6).saturationMod(0.8F).build();
}
