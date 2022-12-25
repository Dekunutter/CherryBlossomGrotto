package com.deku.cherryblossomgrotto.common.items;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import static com.deku.cherryblossomgrotto.common.utils.VanillaAttributeUUIDs.ATTACK_DAMAGE_UUID;
import static com.deku.cherryblossomgrotto.common.utils.VanillaAttributeUUIDs.ATTACK_SPEED_UUID;

public class Katana extends SwordItem {
    public Katana() {
        super(Tiers.IRON, 4, -3.4f, new Item.Properties());
    }

    /**
     * Gets the damage that this weapon will inflict.
     *
     * @return The final damage value
     */
    @Override
    public float getDamage() {
        return super.getDamage();
    }

    /**
     * Determines how fast the katana will destroy certain blocks
     * Copied from source as an override to change cobweb destroy speed with this item
     *
     * @param itemStack the stack of items involved
     * @param state The state of the block being interacted with
     * @return The speed at which the block will be destroyed
     */
    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 20.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    /**
     * Override of a forge hook that is used whenever a modded item's attribute modifiers are being called.
     * It is item stack sensitive so it invokes the attribute modifiers of the given stack instead of the item class itself.
     *
     * @param slotType The equipment slot type that we are getting modifiers for
     * @param stack The instance of the item that we want to see the modifiers for
     * @return
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slotType, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifierMap = LinkedHashMultimap.create();

        if (slotType != EquipmentSlot.MAINHAND) {
            return modifierMap;
        }

        CompoundTag compoundnbt = stack.getTag();
        double foldedSteelStrength = 0;
        if (compoundnbt != null) {
            int currentFolds = compoundnbt.getInt("folds");

            if (currentFolds > 0 && currentFolds < 1000) {
                foldedSteelStrength = currentFolds * 0.1D;
            } else if (currentFolds >= 1000) {
                foldedSteelStrength = Integer.MAX_VALUE;
            }
        }

        modifierMap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 4 + Tiers.IRON.getAttackDamageBonus() + foldedSteelStrength, AttributeModifier.Operation.ADDITION));

        // Changing this value seems to have weird side-effects. It translates the weapon in hand and when set to really low values it kills the attack damage modifier?
        modifierMap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Weapon modifier", (double) -2.4f, AttributeModifier.Operation.ADDITION));
        return modifierMap;
    }
}
