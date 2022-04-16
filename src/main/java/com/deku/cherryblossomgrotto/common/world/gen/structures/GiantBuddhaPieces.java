package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.loot_tables.ModLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class GiantBuddhaPieces {
    private static final ResourceLocation GIANT_BUDDHA_COMPLETE = new ResourceLocation(MOD_ID,"giant_buddha");

    /**
     * Adds all the individual pieces of the structure to an array so that the structure can be built in its entirely from individual NBTs.
     *
     * @param manager The template manager that will be used to load the NBT template from resources
     * @param position The position of the structure
     * @param rotation The rotaion of the structure
     * @param pieceAccessor Accessor for the array of individual pieces we want to append to so that the structure generate all pieces during generation
     * @param random A random number generator
     */
    public static void addPieces(StructureManager manager, BlockPos position, Rotation rotation, StructurePieceAccessor pieceAccessor, Random random) {
        pieceAccessor.addPiece(new GiantBuddhaPieces.Piece(manager, GIANT_BUDDHA_COMPLETE, position, rotation, 0));
    }

    /**
     * Inner class that represents an individual piece of the structure
     */
    public static class Piece extends TemplateStructurePiece {
        public Piece(StructureManager manager, ResourceLocation resourceLocation, BlockPos position, Rotation rotation, int offsetY) {
            super(ModStructurePieceTypes.GIANT_BUDDHA_PIECE, 0, manager, resourceLocation, manager.toString(), loadTemplate(manager, resourceLocation, rotation), position.offset(0, -offsetY, 0));
        }

        public Piece(StructurePieceSerializationContext serializationContext, CompoundTag compoundNBT) {
            super(ModStructurePieceTypes.GIANT_BUDDHA_PIECE, compoundNBT, serializationContext.structureManager(), (placementSettings) -> {
                ResourceLocation templateLocation = new ResourceLocation(compoundNBT.getString("Template"));
                Rotation rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
                return loadTemplate(serializationContext.structureManager(), templateLocation, rotation);
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
        private static StructurePlaceSettings loadTemplate(StructureManager manager, ResourceLocation resourceLocation, Rotation rotation) {
            StructureTemplate template = manager.getOrCreate(resourceLocation);

            BlockPos pivot = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
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
            compoundNBT.putString("Template", this.templateName);
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
        protected void handleDataMarker(String dataMarker, BlockPos position, ServerLevelAccessor levelAccessor, Random random, BoundingBox boundingBox) {
            if ("false_chest".equals(dataMarker)) {
                levelAccessor.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
                ChestBlockEntity.setLootTable(levelAccessor, random, position.below(), ModLootTables.GIANT_BUDDHA_FAKE_CHEST_LOOT_TABLE);
            } else if ("real_chest".equals(dataMarker)) {
                levelAccessor.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
                ChestBlockEntity.setLootTable(levelAccessor, random, position.below(), ModLootTables.GIANT_BUDDHA_REAL_CHEST_LOOT_TABLE);
            }
        }
    }
}
