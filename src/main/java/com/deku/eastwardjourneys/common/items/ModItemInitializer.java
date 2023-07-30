package com.deku.eastwardjourneys.common.items;

import com.deku.eastwardjourneys.common.entity.ModEntityTypeInitializer;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModItemInitializer {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> KOI_BUCKET = ITEMS.register("koi_bucket", () ->
        new KoiBucket(ModEntityTypeInitializer.KOI_ENTITY_TYPE)
    );

    public static final RegistryObject<Item> KOI_SPAWN_EGG = ITEMS.register("koi_spawn_egg", () ->
        new ForgeSpawnEggItem(ModEntityTypeInitializer.KOI_ENTITY_TYPE, 15724527, 16030538, new Item.Properties())
    );

    public static final RegistryObject<Item> TANOOKI_SPAWN_EGG = ITEMS.register("tanooki_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE, 4604732, 9078636, new Item.Properties())
    );

    public static final RegistryObject<Item> TERRACOTTA_WARRIOR_SPAWN_EGG = ITEMS.register("terracotta_warrior_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypeInitializer.TERRACOTTA_WARRIOR_ENTITY_TYPE, 9991201, 12748579, new Item.Properties())
    );
}
