package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.entity.ModEntityTypeInitializer;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModItemInitializer {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> KOI_BUCKET = ITEMS.register("koi_bucket", () ->
        new KoiBucket(ModEntityTypeInitializer.KOI_ENTITY_TYPE)
    );

    public static final RegistryObject<Item> KOI_SPAWN_EGG = ITEMS.register("koi_spawn_egg", () ->
        new ForgeSpawnEggItem(ModEntityTypeInitializer.KOI_ENTITY_TYPE, 15724527, 16030538, new Item.Properties())
    );

    public static final RegistryObject<Item> CORALFISH_SPAWN_EGG = ITEMS.register("coralfish_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypeInitializer.CORALFFISH_ENTITY_TYPE, 9738135, 3814711, new Item.Properties())
    );

    // TODO: Select some proper colours or this spawn egg
    public static final RegistryObject<Item> TANOOKI_SPAWN_EGG = ITEMS.register("tanooki_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE, 9738135, 3814711, new Item.Properties())
    );
}
