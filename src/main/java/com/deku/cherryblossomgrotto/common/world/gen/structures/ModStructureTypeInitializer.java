package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructureTypeInitializer {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MOD_ID);

    public static final RegistryObject<StructureType<GiantBuddha>> GIANT_BUDDHA = STRUCTURE_TYPES.register("giant_buddha", () -> convert(GiantBuddha.CODEC));
    public static final RegistryObject<StructureType<ToriiGate>> TORII_GATE = STRUCTURE_TYPES.register("torii_gate", () -> convert(ToriiGate.CODEC));
    public static final RegistryObject<StructureType<RuinedToriiPortal>> RUINED_TORII_PORTAL = STRUCTURE_TYPES.register("ruined_torii_portal", () -> convert(RuinedToriiPortal.CODEC));
    public static final RegistryObject<StructureType<Gazebo>> GAZEBO = STRUCTURE_TYPES.register("gazebo", () -> convert(Gazebo.CODEC));

    /**
     * Converts a given codec for cleaner use in the structure type registry
     *
     * @param codec Codec to be converted
     * @return The structure type for a given structure, extracted from a given codec
     * @param <S> Structure embedded in the codec
     */
    private static <S extends Structure>  StructureType<S> convert(Codec<S> codec) {
        return () -> codec;
    }
}
