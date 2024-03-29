package com.deku.cherryblossomgrotto.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DoubleJumpEnchantment extends Enchantment {
    protected DoubleJumpEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    /**
     * Whether the given item can be enchanted by this enchantment
     *
     * @param itemStack The item we are trying to enchant
     * @return Whether the item can have this enchantment applied
     */
    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof ArmorItem && ((ArmorItem)itemStack.getItem()).getEquipmentSlot() == EquipmentSlot.FEET;
    }
    /**
     * Whether this enchantment can only be found in treasure
     *
     * @return Whether this enchantment is treasure only
     */
    @Override
    public boolean isTreasureOnly()  {
        return false;
    }

    /**
     * Whether this enchantment can be acquired by trading
     *
     * @return Whether this enchantment is tradeable
     */
    @Override
    public boolean isTradeable() {
        return true;
    }

    /**
     * Whether this enchantment is discoverable on an enchanting table
     *
     * @return Whether this enchantment is discoverable.
     */
    @Override
    public boolean isDiscoverable() {
        return true;
    }

    /**
     * The minimum cost of applying this enchantment
     *
     * @param cost Average cost of this enchantment
     * @return Minimum cost of applying this enchantment
     */
    @Override
    public int getMinCost(int cost) {
        return cost * 10;
    }

    /**
     * The maximum cost of applying this enchantment
     *
     * @param cost Average cost of this enchantment
     * @return Maximum cost of applying this enchantment
     */
    @Override
    public int getMaxCost(int cost) {
        return getMinCost(cost) + 15;
    }

    /**
     * The maximum level that this enchantment can be increase to
     *
     * @return Maximum level of this enchantment
     */
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
