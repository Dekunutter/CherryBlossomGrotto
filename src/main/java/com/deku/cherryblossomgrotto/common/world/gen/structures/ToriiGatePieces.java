package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;


import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ToriiGatePieces {
    private static final ResourceLocation TORII_GATE_COMPLETE = new ResourceLocation(MOD_ID, "torii_gate");

    /**
     * Adds all the individual pieces of the structure to an array so that the structure can be built in its entirely from individual NBTs.
     *
     * @param manager The template manager that will be used to load the NBT template from resources
     * @param position The position of the structure
     * @param rotation The rotaion of the structure
     * @param pieceAccessor Accessor for the array of individual pieces we want to append to so that the structure generate all pieces during generation
     * @param random A random number generator
     */
    public static void addPieces(StructureTemplateManager manager, BlockPos position, Rotation rotation, StructurePieceAccessor pieceAccessor, RandomSource random) {
        pieceAccessor.addPiece(new ToriiGatePieces.Piece(manager, TORII_GATE_COMPLETE, position, rotation, 0));
    }

    /**
     * Inner class that represents an individual piece of the structure
     */
    public static class Piece extends TemplateStructurePiece {
        public Piece(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos position, Rotation rotation, int offsetY) {
            super(ModStructurePieceTypes.TORII_GATE_PIECE, 0, manager, resourceLocation, resourceLocation.toString(), loadTemplate(manager, resourceLocation, rotation), position.offset(BlockPos.ZERO).below(offsetY));
        }

        public Piece(StructurePieceSerializationContext serializationContext, CompoundTag compoundNBT) {
            super(ModStructurePieceTypes.TORII_GATE_PIECE, compoundNBT, serializationContext.structureTemplateManager(), (placementSettings) -> {
                ResourceLocation templateLocation = new ResourceLocation(compoundNBT.getString("Template"));
                Rotation rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
                return loadTemplate(serializationContext.structureTemplateManager(), templateLocation, rotation);
            });
        }

        /**
         * Loads the NBT template for this piece of the structure.
         * Also sets the placement settings of the piece and ensures core values are set in state for this piece.
         *
         * @param manager The template manager we will use to load the piece from an NBT template
         * @param resourceLocation Location in resources containing the structure piece config
         * @param rotation Rotation of the structure
         */
        private static StructurePlaceSettings loadTemplate(StructureTemplateManager manager, ResourceLocation resourceLocation, Rotation rotation) {
            StructureTemplate template = manager.getOrCreate(resourceLocation);

            BlockPos pivot = new BlockPos(template.getSize().getX() / 2 , 0, template.getSize().getZ() / 2);
            return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).setRotationPivot(pivot).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        /**
         * Adds additional save data to the NBT template.
         *
         * @param serializationContext Context for the serialized structure piece information
         * @param compoundNBT The NBT that we are putting additional information into
         */
        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext serializationContext, CompoundTag compoundNBT) {
            super.addAdditionalSaveData(serializationContext, compoundNBT);
            compoundNBT.putString("Rot", this.placeSettings.getRotation().name());
        }

        /**
         * Handles the assignment of additional data to specific blocks in the NBT template.
         * This includes the assignment of loot tables to individual chests.
         *
         * @param dataMarker the data marker we want to handle
         * @param position Position of the block with a data marker
         * @param levelAccessor Accessor for the level that the data marker is in
         * @param random A random number generator
         * @param boundingBox The bounding box of the structure piece
         */
        @Override
        protected void handleDataMarker(String dataMarker, BlockPos position, ServerLevelAccessor levelAccessor, RandomSource random, BoundingBox boundingBox) {
            return;
        }
    }
}
