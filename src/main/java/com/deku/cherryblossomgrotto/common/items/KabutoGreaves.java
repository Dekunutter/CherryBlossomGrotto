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
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class KabutoGreaves extends ArmorItem implements IItemRenderProperties {
    public KabutoGreaves() {
        super(ArmorMaterials.IRON, EquipmentSlot.LEGS, new Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT));
        setRegistryName("kabuto_greaves");
    }

    /**
     * Ensures that the client renders the current model for this piece of armour
     *
     * @param consumer The consumer containing render properties for this item
     */
    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
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
            public final HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultArmor) {
                return KabutoArmourLayer.INNER_MODEL;
            }
        });
    }

    /**
     * Gets the texture for this piece of armour.
     *
     * @param stack The item stack this item came from
     * @param entity The entity equipping this piece of armour
     * @param slot The slot the armour is being equipped in
     * @param type Whether the texture is a default texture (null) or an overlay (overlay)
     * @return The resource location for this texture
     */
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return KabutoArmourLayer.INNER_MODEL.getTexture();
    }
}
