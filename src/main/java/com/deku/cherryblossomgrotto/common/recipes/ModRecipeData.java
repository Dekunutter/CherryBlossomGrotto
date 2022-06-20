package com.deku.cherryblossomgrotto.common.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModRecipeData {
    @ObjectHolder("folding")
    public static RecipeSerializer<FoldingRecipe> FOLDING_DATA;
}
