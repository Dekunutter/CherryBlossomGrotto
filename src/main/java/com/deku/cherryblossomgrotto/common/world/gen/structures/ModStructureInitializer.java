package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureInitializer {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);

    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> GIANT_BUDDHA = STRUCTURES.register("giant_buddha", GiantBuddha::new);
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> TORII_GATE = STRUCTURES.register("torii_gate", ToriiGate::new);
}
