package com.deku.cherryblossomgrotto.common.world.gen.topLayerModifications;

import com.deku.cherryblossomgrotto.common.features.CherryBlossomPetalCoverFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTopLayerModifications {
    public static final DeferredRegister<Feature<?>> TOP_LAYER_MODIFICATIONS = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CHERRY_BLOSSOM_GROUND_COVER = TOP_LAYER_MODIFICATIONS.register("cherry_blossom_petal_ground_cover", CherryBlossomPetalCoverFeature::new);
}
