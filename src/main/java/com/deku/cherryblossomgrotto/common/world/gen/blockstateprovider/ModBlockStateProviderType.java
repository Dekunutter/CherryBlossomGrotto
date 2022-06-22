package com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider;

import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBlockStateProviderType {
    @ObjectHolder(registryName = "minecraft:worldgen/block_state_provider_type", value = MOD_ID + ":cherry_blossom_forest_flower_provider")
    public static CherryBlossomForestFlowerProviderType CHERRY_FOREST_FLOWERS;
}
