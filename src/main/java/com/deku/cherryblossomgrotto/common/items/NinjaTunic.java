package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.client.renderers.layers.NinjaRobesLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NinjaTunic extends ArmorItem {
    public NinjaTunic() {
        super(ModArmorMaterials.WOOL, Type.CHESTPLATE, new Item.Properties().stacksTo(1));
    }

    /**
     * Ensures that the client renders the current model for this piece of armour
     *
     * @param consumer The consumer containing render properties for this item
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            /**
             * Gets the model for this piece of armour once it has been equipped
             * Called by the Forge armour model hook during the humanoid armor layer render.
             *
             * @param entity The entity equipping this piece of armour
             * @param itemStack The item stack this item came from
             * @param armorSlot The slot the armour is being equipped in
             * @param defaultArmor The default armour model for this entity
             * @return The armour model of the equipped piece of armour
             */
            @Nullable
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultArmor) {
                return NinjaRobesLayer.MODEL;
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
