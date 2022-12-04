package com.deku.cherryblossomgrotto.common.recipes;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModRecipeSerializerInitializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registry.RECIPE_SERIALIZER_REGISTRY, MOD_ID);

    public static final RegistryObject<SimpleRecipeSerializer<FoldingRecipe>> FOLDING_SERIALIZER = RECIPE_SERIALIZERS.register("folding", () -> new SimpleRecipeSerializer<>(FoldingRecipe::new));
}
