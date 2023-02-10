package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.entity.ModEntityTypeInitializer;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModItemInitializer {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> KOI_BUCKET = ITEMS.register("koi_bucket", () ->
            new KoiBucket(() -> ModEntityTypeInitializer.KOI_ENTITY_TYPE.get())
    );
}
