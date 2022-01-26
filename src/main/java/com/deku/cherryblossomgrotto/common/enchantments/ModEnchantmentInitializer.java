package com.deku.cherryblossomgrotto.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModEnchantmentInitializer {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MOD_ID);

    public static final RegistryObject<Enchantment> DOUBLE_JUMP_ENCHANTMENT = ENCHANTMENTS.register("double_jump_enchantment", DoubleJumpEnchantment::new);
}
