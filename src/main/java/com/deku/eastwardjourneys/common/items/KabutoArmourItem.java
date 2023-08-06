package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.client.renderers.KabutoArmourRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class KabutoArmourItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public KabutoArmourItem(ArmorItem.Type type) {
        super(ArmorMaterials.IRON, type, new Properties().stacksTo(1));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;
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
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> defaultArmor) {
                if (renderer == null) {
                    renderer = new KabutoArmourRenderer();
                }
                renderer.prepForRender(entity, itemStack, armorSlot, defaultArmor);
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "Controller", 10, state -> state.setAndContinue(DefaultAnimations.IDLE))
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
