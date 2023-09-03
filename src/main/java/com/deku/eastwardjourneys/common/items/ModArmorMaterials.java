package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.Main;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    WOOL("wool", 4, Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 1);
        map.put(ArmorItem.Type.CHESTPLATE, 1);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
        return Ingredient.of(Items.BLACK_WOOL);
    });

    // NOTE: Copied from vanilla ArmourMaterials
    private static final EnumMap HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, EnumMap protections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient.get();
    }

    /**
     * Gets the durability of the armour piece for the given slot it is equipped in.
     * This is a combination of the health of each slot (a constant array inherited from the base game's
     * armour material class) and a durability multiplier.
     *
     * @param type The type of slot that this armour piece is intended to be equipped in
     * @return The durability of this piece of armour
     */
    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return (Integer) HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    /**
     * Gets the defense provided by wearing a piece of this armour in the given slot.
     * Read from the slot protections array specified in the enum definition.
     *
     * @param type the type of slot that this armour piece is intended to be equipped in
     * @return Defense granted by this piece of armour
     */
    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return protectionFunctionForType.get(type);
    }

    /**
     * Determines how easily enchantable this item is.
     *
     * @return The enchantability of the armour piece.
     */
    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    /**
     * The sound that plays when the armour piece is equipped.
     *
     * @return Sound event to play when the armour piece is equipped.
     */
    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    /**
     * The ingredient that is used to repair this piece of armour.
     *
     * @return The ingredient that will repair this armour piece.
     */
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    /**
     * The name of this armour material enum
     *
     * @return The name (with mod ID) of this armour material
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return Main.MOD_ID + ":" + this.name;
    }

    /**
     * The toughness of this armour material.
     *
     * @return Toughness of this armour material.
     */
    @Override
    public float getToughness() {
        return this.toughness;
    }

    /**
     * How much resistance this armour material has to knockback.
     *
     * @return Knockback resistance of this armour material
     */
    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
