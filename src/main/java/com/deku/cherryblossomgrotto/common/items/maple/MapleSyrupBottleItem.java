package com.deku.cherryblossomgrotto.common.items.maple;

import com.deku.cherryblossomgrotto.common.foods.ModFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class MapleSyrupBottleItem extends Item {
    private static final int DRINK_DURATION = 40;

    public MapleSyrupBottleItem() {
        super(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(ModFoods.MAPLE_SYRUP_BOTTLE).stacksTo(16));
    }

    /**
     * Logic that happens when the item has been used.
     * For this item, we want to trigger an effect on the consumer and consume the contents,
     * leaving behind an empty bottle in its place.
     * Maple syrup (like honey) will cure poison but will also grant a brief swiftness buff
     *
     * @param itemStack The item being used
     * @param level The level in which the item was used
     * @param entity The entity that used the item
     * @return
     */
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        super.finishUsingItem(itemStack, level, entity);
        if (entity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            entity.removeEffect(MobEffects.POISON);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800));
        }

        if (itemStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (entity instanceof Player && !((Player)entity).getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                Player player = (Player)entity;
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return itemStack;
        }
    }

    /**
     * The time it takes for the contents of the bottle to be used when interacting with the item
     *
     * @param itemStack The item being used
     * @return Time needed to consume the bottle's contents
     */
    public int getUseDuration(ItemStack itemStack) {
        return DRINK_DURATION;
    }

    /**
     * Animation that triggers when the item is used
     *
     * @param itemStack The item being used
     * @return Animation that plays while the item is being consumed
     */
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    /**
     * The sound that plays when the item is being drunk
     *
     * @return Sound that plays
     */
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    /**
     * The sound that plays when the item is eaten
     *
     * @return Sound that plays
     */
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    /**
     * Logic that occurs while the item is being used
     *
     * @param level Level that the item is being used in
     * @param player Player using the item
     * @param hand Hand with which the player is using the item
     * @return The interaction result occurring from the use of the item
     */
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
