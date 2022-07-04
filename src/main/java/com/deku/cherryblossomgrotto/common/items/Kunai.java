package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;


public class Kunai extends ProjectileWeaponItem implements Vanishable {
    private static final float FLIGHT_DROP = 0.0f;
    private static final float FLIGHT_SPEED = 1.5f;
    private static final float VARIATION = 5.0f;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public Kunai() {
        super(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_COMBAT));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 2.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-1.0F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets the use action animation that plays out when a living entity uses this item.
     *
     * @param itemStack The item being used
     * @return The use action animation to invoke
     */
    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }

    /**
     * Logic that plays out whenever a player uses this item.
     * This item passes along an action result that allows us to process subsequent usage actions like
     * processing a player holding back the item in subsequent function calls
     *
     * @param level The level the item is being used within
     * @param player The player using the item
     * @param hand The hand the player is using the item with
     * @return The action result of using this item
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        return InteractionResultHolder.pass(itemstack);
    }

    /**
     * Logic that is called whenever the user completes a use action on this item.
     * This spawns an entity into the world.
     *
     * @param itemstack The item being used
     * @param level The level the item is being used within
     * @param user The entity using this item
     * @param ticks The number of ticks that this item was being used for
     */
    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity user, int ticks) {
        if (!level.isClientSide) {
            KunaiEntity entity = new KunaiEntity(user, level);
            entity.shootFromRotation(user, user.getXRot(), user.getYRot(), FLIGHT_DROP, FLIGHT_SPEED, VARIATION);
            level.addFreshEntity(entity);
        }

        if (user instanceof Player) {
            Player player = (Player) user;
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
        }
    }


    /**
     * Returns the default attribute modifiers for this item given a specific slot that it is being used in
     *
     * @param slotType The type of slot the item is being used within
     * @return The default attribute modifiers for this item in the given slot type
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slotType) {
        return slotType == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slotType);
    }

    /**
     * A list of all projectiles that this shootable item is compatible with
     *
     * @return all items that this shooter is compatible with, in a predicate
     */
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (itemStack) -> itemStack.getItem() == ModItems.KUNAI;
    }

    /**
     * Gets the default range of projectiles from this shooter
     *
     * @return The default range for projectiles from this shooter
     */
    @Override
    public int getDefaultProjectileRange() {
        return 10;
    }

    /**
     * Sets a flag that means the item will not be used up until the entity releases the use action
     *
     * @param itemStack The item being used
     * @return Result of the flag for whether item is used on press, or on release
     */
    @Override
    public boolean useOnRelease(ItemStack itemStack) {
        return true;
    }

    /**
     * Gets the duration that the item can be used for
     *
     * @param itemStack The item being used
     * @return The use duration for this item
     */
    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 200;
    }
}
