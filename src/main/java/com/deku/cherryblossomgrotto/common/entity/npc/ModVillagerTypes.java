package com.deku.cherryblossomgrotto.common.entity.npc;

import com.deku.cherryblossomgrotto.common.items.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVillagerTypes {
    public static VillagerType CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE;

    public static void register() {
        bootstrapVillagerTrades();

        CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE = Registry.register(BuiltInRegistries.VILLAGER_TYPE, new ResourceLocation(MOD_ID, "cherry_blossom_grotto"), new VillagerType("cherry_blossom_grotto"));

        updateVillagerTrades();
    }

    /**
     * Bootstrapping the static villager trade map so that it is loaded into memory.
     * Performing some rudimentary size check just to have an excuse to call the class.
     * This function should be called BEFORE any new villager types are registered so that the trades can load successfully
     */
    private static void bootstrapVillagerTrades() {
        VillagerTrades.TRADES.size();
    }

    /**
     * Updates villager trades in the vanilla game to account for the new villager type.
     * Basically just adds a boat trade for the new villager type so that the trades list doesn't cause a crash down the line.
     */
    private static void updateVillagerTrades() {
        Int2ObjectMap<VillagerTrades.ItemListing[]> fishermanTrades = VillagerTrades.TRADES.get(VillagerProfession.FISHERMAN);
        VillagerTrades.EmeraldsForVillagerTypeItem boatTradesType = (VillagerTrades.EmeraldsForVillagerTypeItem) fishermanTrades.get(5)[1];

        Map<VillagerType, Item> trades = boatTradesType.trades;
        Map<VillagerType, Item> newTrades = new HashMap<>(trades);
        newTrades.put(ModVillagerTypes.CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE, ModItems.CHERRY_BOAT);
        boatTradesType.trades = newTrades;
    }
}
