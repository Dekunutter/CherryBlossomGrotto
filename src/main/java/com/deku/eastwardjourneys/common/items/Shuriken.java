package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.entity.projectile.ShurikenEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class Shuriken extends ProjectileWeaponItem implements Vanishable {
    private static final float FLIGHT_DROP = 0.0f;
    private static final float FLIGHT_SPEED = 6.0f;
    private static final float VARIATION = 0.0f;

    public Shuriken() {
        super(new Item.Properties().stacksTo(16));
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
     * processing a player holding back the item in subsequent function calls.
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
     * Logic that is called during every tick that the user is continuing to use this item
     *
     * @param level The level the item is being used within
     * @param user The entity using this item
     * @param itemstack The item being used
     * @param ticks The number of ticks that this item has been used for
     */
    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack itemstack, int ticks) {
    }

    /**
     * Logic that is called whenever the user completes a use action on this item.
     * Spawns an entity in the world for this item.
     *
     * @param itemstack The item being used
     * @param level The level the item is being used within
     * @param user The entity using this item
     * @param ticks The number of ticks that this item was being used for
     */
    public void releaseUsing(ItemStack itemstack, Level level, LivingEntity user, int ticks) {
        if (!level.isClientSide) {
            ShurikenEntity entity = new ShurikenEntity(user, level);

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

    /**
     * A list of all projectiles that this shootable item is compatible with
     *
     * @return all items that this shooter is compatible with, in a predicate
     */
    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (itemStack) -> itemStack.getItem() == ModItems.SHURIKEN;
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
}
