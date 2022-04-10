package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.client.renderers.layers.NinjaRobesLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NinjaSandals extends ArmorItem {
    public NinjaSandals() {
        super(ModArmorMaterials.WOOL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
        setRegistryName("ninja_sandals");
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            /**
             * Gets the model for this piece of armour once it has been equipped
             *
             * @param entity The entity equipping this piece of armour
             * @param itemStack The item stack this item came from
             * @param armorSlot The slot the armour is being equipped in
             * @param defaultArmor The default armour model for this entity
             * @return The armour model of the equipped piece of armour
             */
            @Nullable
            @Override
            public final HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultArmor) {
                return NinjaRobesLayer.MODEL.applyEntityStats(defaultArmor);
            }
        });
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
        return NinjaRobesLayer.MODEL.getTexture();
    }
}
