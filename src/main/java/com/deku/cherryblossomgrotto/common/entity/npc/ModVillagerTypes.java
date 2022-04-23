package com.deku.cherryblossomgrotto.common.entity.npc;

import com.deku.cherryblossomgrotto.Main;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModVillagerTypes {
    public static VillagerType CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE;

    public static void register() {
        Main.LOGGER.info("HELLO from Register Villager Type");

        bootstrapVillagerTrades();

        CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE = Registry.register(Registry.VILLAGER_TYPE, new ResourceLocation(MOD_ID, "cherry_blossom_grotto"), new VillagerType("cherry_blossom_grotto"));

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

        // TODO: Could determine this dynamically so if the trade map was updated somewhere else, it just appends to whatever is in the current map instead of overwriting
        ModVillagerBoatTrade newBoatTrade = new ModVillagerBoatTrade(
            1,
            12,
            30,
            ImmutableMap.<VillagerType, Item>builder()
            .put(VillagerType.PLAINS, Items.OAK_BOAT)
            .put(VillagerType.TAIGA, Items.SPRUCE_BOAT)
            .put(VillagerType.SNOW, Items.SPRUCE_BOAT)
            .put(VillagerType.DESERT, Items.JUNGLE_BOAT)
            .put(VillagerType.JUNGLE, Items.JUNGLE_BOAT)
            .put(VillagerType.SAVANNA, Items.ACACIA_BOAT)
            .put(VillagerType.SWAMP, Items.DARK_OAK_BOAT)
            .put(ModVillagerTypes.CHERRY_BLOSSOM_GROTTO_VILLAGER_TYPE, ModItems.CHERRY_BOAT)
            .build()
        );
        fishermanTrades.get(5)[1] = newBoatTrade;

        VillagerTrades.TRADES.put(VillagerProfession.FISHERMAN, fishermanTrades);

        Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> ff = VillagerTrades.TRADES;
    }
}
