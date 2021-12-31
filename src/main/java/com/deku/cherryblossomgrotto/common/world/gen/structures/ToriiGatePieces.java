package com.deku.cherryblossomgrotto.common.world.gen.structures;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ToriiGatePieces {
    private static final ResourceLocation TORII_GATE_COMPLETE = new ResourceLocation(MOD_ID, "torii_gate");

    /**
     * Adds all the individual pieces of the structure to an array so that the structure can be built in its entirely from individual NBTs.
     *
     * @param templateManager The template manager that will be used to load the NBT template from resources
     * @param position The position of the structure
     * @param rotation The rotaion of the structure
     * @param pieces The array of individual pieces we want to append to so that the structure generate all pieces during generation
     * @param random A random number generator
     */
    public static void addPieces(TemplateManager templateManager, BlockPos position, Rotation rotation, List<StructurePiece> pieces, Random random) {
        pieces.add(new ToriiGatePieces.Piece(templateManager, TORII_GATE_COMPLETE, position, rotation, 0));
    }

    /**
     * Inner class that represents an individual piece of the structure
     */
    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateLocation;
        private final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos position, Rotation rotation, int offsetY) {
            super(ModStructurePieceTypes.TORII_GATE_PIECE, 0);
            this.templateLocation = resourceLocation;
            this.templatePosition = position.offset(0, -offsetY, 0);
            this.rotation = rotation;
            this.loadTemplate(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(ModStructurePieceTypes.TORII_GATE_PIECE, compoundNBT);
            this.templateLocation = new ResourceLocation(compoundNBT.getString("Template"));
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.loadTemplate(templateManager);
        }

        /**
         * Loads the NBT template for this piece of the structure.
         * Also sets the placement settings of the piece and ensures core values are set in state for this piece.
         *
         * @param manager The template manager we will use to load the piece from an NBT template
         */
        private void loadTemplate(TemplateManager manager) {
            Template template = manager.getOrCreate(this.templateLocation);

            BlockPos pivot = new BlockPos(template.getSize().getX() / 2 , 0, template.getSize().getZ() / 2);
            PlacementSettings settings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setRotationPivot(pivot).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);

            this.setup(template, this.templatePosition, settings);
        }

        /**
         * Adds additional save data to the NBT template.
         *
         * @param compoundNBT The NBT that we are putting additional information into
         */
        @Override
        protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
            super.addAdditionalSaveData(compoundNBT);
            compoundNBT.putString("Template", this.templateLocation.toString());
            compoundNBT.putString("Rot", this.rotation.name());
        }

        /**
         * Handles the assignment of additional data to specific blocks in the NBT template.
         * This includes the assignment of loot tables to individual chests.
         *
         * @param dataMarker the data marker we want to handle
         * @param position Position of the block with a data marker
         * @param world The world that the data marker is in
         * @param random A random number generator
         * @param boundingBox The bounding box of the structure piece
         */
        @Override
        protected void handleDataMarker(String dataMarker, BlockPos position, IServerWorld world, Random random, MutableBoundingBox boundingBox) {
            return;
        }
    }
}
