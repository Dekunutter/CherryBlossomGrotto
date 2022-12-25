package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MOD_ID);

    public static RegistryObject<TrunkPlacerType<CherryBlossomTrunkPlacer>> CHERRY_TREE_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("cherry_blossom_tree_trunk_placer", () -> new TrunkPlacerType<>(CherryBlossomTrunkPlacer.CODEC));
    public static RegistryObject<TrunkPlacerType<FancyCherryBlossomTrunkPlacer>> FANCY_CHERRY_TREE_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("fancy_cherry_blossom_tree_trunk_placer", () -> new TrunkPlacerType<>(FancyCherryBlossomTrunkPlacer.CODEC));
    public static RegistryObject<TrunkPlacerType<GrandCherryBlossomTrunkPlacer>> GRAND_CHERRY_TREE_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("grand_cherry_blossom_tree_trunk_placer", () -> new TrunkPlacerType<>(GrandCherryBlossomTrunkPlacer.CODEC));
}
