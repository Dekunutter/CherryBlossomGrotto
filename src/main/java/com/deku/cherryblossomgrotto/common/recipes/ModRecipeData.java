package com.deku.cherryblossomgrotto.common.recipes;

import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModRecipeData {
    @ObjectHolder("folding")
    public static SpecialRecipeSerializer<FoldingRecipe> FOLDING_DATA;
}
