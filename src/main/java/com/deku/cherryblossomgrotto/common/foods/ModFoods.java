package com.deku.cherryblossomgrotto.common.foods;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties RICE = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();
    public static final FoodProperties ONIGIRI = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties KOI = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build();
    public static final FoodProperties COOKED_KOI = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties MAPLE_SYRUP_BOTTLE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build();
}
