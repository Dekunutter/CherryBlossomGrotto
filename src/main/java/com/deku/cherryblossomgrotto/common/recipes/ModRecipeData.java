package com.deku.cherryblossomgrotto.common.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModRecipeData {
    @ObjectHolder(registryName = "minecraft:recipe_serializer", value = MOD_ID + ":folding")
    public static RecipeSerializer<FoldingRecipe> FOLDING_DATA;
}
