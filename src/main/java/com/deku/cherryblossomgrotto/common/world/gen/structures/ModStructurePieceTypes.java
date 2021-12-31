package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructurePieceTypes {
    public static IStructurePieceType GIANT_BUDDHA_PIECE = GiantBuddhaPieces.Piece::new;
    public static IStructurePieceType TORII_GATE_PIECE = ToriiGatePieces.Piece::new;

    /**
     * Registers all structure piece types via the structure piece registry
     */
    public static void register() {
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(MOD_ID + "g_b"), GIANT_BUDDHA_PIECE);
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(MOD_ID + "t_g"), TORII_GATE_PIECE);
    }
}
