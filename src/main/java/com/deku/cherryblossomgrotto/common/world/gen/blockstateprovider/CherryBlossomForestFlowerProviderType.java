package com.deku.cherryblossomgrotto.common.world.gen.blockstateprovider;

import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

public class CherryBlossomForestFlowerProviderType extends BlockStateProviderType<CherryBlossomGrottoFlowerBlockStateProvider> {
    public CherryBlossomForestFlowerProviderType() {
        super(CherryBlossomGrottoFlowerBlockStateProvider.CODEC);
        setRegistryName("cherry_blossom_forest_flower_provider");
    }
}
