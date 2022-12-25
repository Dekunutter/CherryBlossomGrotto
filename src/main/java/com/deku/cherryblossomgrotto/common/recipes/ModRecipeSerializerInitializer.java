package com.deku.cherryblossomgrotto.common.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModRecipeSerializerInitializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer<FoldingRecipe>> FOLDING_SERIALIZER = RECIPE_SERIALIZERS.register("folding", () -> new SimpleCraftingRecipeSerializer<>(FoldingRecipe::new));
}
