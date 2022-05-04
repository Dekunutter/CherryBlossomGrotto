package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModStructurePieceTypes {
    public static StructurePieceType GIANT_BUDDHA_PIECE = GiantBuddhaPieces.Piece::new;
    public static StructurePieceType TORII_GATE_PIECE = ToriiGatePieces.Piece::new;

    /**
     * Registers all structure piece types via the structure piece registry
     */
    public static void register() {
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(MOD_ID, "g_b"), GIANT_BUDDHA_PIECE);
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(MOD_ID, "t_g"), TORII_GATE_PIECE);
    }
}
