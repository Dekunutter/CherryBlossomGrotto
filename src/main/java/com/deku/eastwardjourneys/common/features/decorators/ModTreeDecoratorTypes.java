package com.deku.eastwardjourneys.common.features.decorators;

import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, MOD_ID);

    public static RegistryObject<TreeDecoratorType<ShiitakeMushroomDecorator>> SHIITAKE_MUSHROOM_TREE_DECORATOR_TYPE = TREE_DECORATOR_TYPES.register("shiitake_mushroom_tree_decorator", () -> new TreeDecoratorType<>(ShiitakeMushroomDecorator.CODEC));
}
