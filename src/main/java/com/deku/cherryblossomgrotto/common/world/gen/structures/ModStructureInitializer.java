package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureInitializer {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, MOD_ID);

    public static final RegistryObject<StructureType<?>> GIANT_BUDDHA = STRUCTURES.register("giant_buddha", () -> convert(GiantBuddha.CODEC));
    public static final RegistryObject<StructureType<?>> TORII_GATE = STRUCTURES.register("torii_gate", () -> convert(ToriiGate.CODEC));

    private static <S extends Structure>  StructureType<S> convert(Codec<S> codec) {
        return () -> codec;
    }
}
