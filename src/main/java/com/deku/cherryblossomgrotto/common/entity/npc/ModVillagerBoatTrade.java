package com.deku.cherryblossomgrotto.common.entity.npc;

import com.deku.cherryblossomgrotto.Main;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class ModVillagerBoatTrade implements VillagerTrades.ItemListing {
    private final Map<VillagerType, Item> trades;
    private final int cost;
    private final int maxUses;
    private final int villagerXp;

    // NOTE: This class is a copy of the EmeraldsForVillagerTypeItem trade in vanilla, but since that class is privatized, I'm just reimplementing it here quickly for my updated boat trades
    public ModVillagerBoatTrade(int cost, int maxUses, int villageXp, Map<VillagerType, Item> trades) {
        Registry.VILLAGER_TYPE.stream().filter((villagerType) -> {
            Main.LOGGER.info(villagerType);
            return !trades.containsKey(villagerType);
        }).findAny().ifPresent((villagerType) -> {
            throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getKey(villagerType));
        });
        this.trades = trades;
        this.cost = cost;
        this.maxUses = maxUses;
        this.villagerXp = villageXp;
    }

    /**
     * Gets the offer from the merchant
     *
     * @param entity The entity being traded with
     * @param random Random number generator
     * @return The offer returned by the merchant, from item cost, XP gain and the item being traded
     */
    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, Random random) {
        if (entity instanceof VillagerDataHolder) {
            ItemStack itemstack = new ItemStack(this.trades.get(((VillagerDataHolder)entity).getVillagerData().getType()), this.cost);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.05F);
        } else {
            return null;
        }
    }
}
