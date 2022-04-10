package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.client.renderers.layers.KabutoArmourLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class KabutoSandals extends ArmorItem implements IItemRenderProperties {
    public KabutoSandals() {
        super(ArmorMaterials.IRON, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT));
        setRegistryName("kabuto_sandals");
    }

    /**
     * Gets the model for this piece of armour once it has been equipped
     *
     * @param entity The entity equipping this piece of armour
     * @param itemStack The item stack this item came from
     * @param armorSlot The slot the armour is being equipped in
     * @param defaultArmor The default armour model for this entity
     * @return The armour model of the equipped piece of armour
     */
    @Override
    public final HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultArmor) {
        return KabutoArmourLayer.MODEL.applyEntityStats(defaultArmor);
    }

    /**
     * Gets the texture for this piece of armour
     *
     * @param stack The item stack this item came from
     * @param entity The entity equipping this piece of armour
     * @param slot The slot the armour is being equipped in
     * @param type Whether the texture is a default texture (null) or an overlay (overlay)
     * @return The resource location for this texture
     */
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return KabutoArmourLayer.MODEL.getTexture();
    }
}
