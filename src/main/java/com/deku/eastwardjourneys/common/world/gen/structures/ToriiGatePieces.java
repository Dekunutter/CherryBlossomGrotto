package com.deku.eastwardjourneys.common.world.gen.structures;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;


import java.util.Map;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ToriiGatePieces {
    static final ResourceLocation TORII_GATE_BOTTOM = new ResourceLocation(MOD_ID, "torii_gate_bottom");
    private static final ResourceLocation TORII_GATE_TOP = new ResourceLocation(MOD_ID, "torii_gate_top");

    private static final Map<ResourceLocation, BlockPos> OFFSETS = ImmutableMap.of(TORII_GATE_BOTTOM, BlockPos.ZERO, TORII_GATE_TOP, new BlockPos(0, 5, -3));

    /**
     * Adds all the individual pieces of the structure to an array so that the structure can be built in its entirely from individual NBTs.
     * Does some pre-generation checks to ensure the structure can actually spawn in the given position.
     *
     * @param manager The template manager that will be used to load the NBT template from resources
     * @param position The position of the structure
     * @param rotation The rotaion of the structure
     * @param pieceAccessor Accessor for the array of individual pieces we want to append to so that the structure generate all pieces during generation
     * @param generationContext Context for the generator for the chunk the structure is being built within
     */
    public static void addPieces(StructureTemplateManager manager, BlockPos position, Rotation rotation, StructurePieceAccessor pieceAccessor, Structure.GenerationContext generationContext) {
        ToriiGatePieces.Piece gateBottom = new ToriiGatePieces.Piece(manager, TORII_GATE_BOTTOM, position, rotation, 0);

        if (!checkIfSupportPositionIsValid(gateBottom, gateBottom.getBoundingBox().maxX(), gateBottom.getBoundingBox().maxZ(), generationContext)) {
            return;
        }

        pieceAccessor.addPiece(gateBottom);
        pieceAccessor.addPiece(new ToriiGatePieces.Piece(manager, TORII_GATE_TOP, position, rotation, 0));
    }

    /**
     * Checks if the given position is valid for spawning one of the strucutre's supports
     *
     * @param initialSupport The pre-generated piece that will end up being the origin piece of this structure
     * @param positionX The position of the other support for this structure that we are checking validity of, on the X axis
     * @param positionZ The position of the other support for this structure that we are checking validity of, on the Z axis
     * @param generationContext Context for the generator for the chunk the structure is being built within
     * @return Whether the proposed support position is valid for the given structure.
     */
    private static boolean checkIfSupportPositionIsValid(ToriiGatePieces.Piece initialSupport, int positionX, int positionZ, Structure.GenerationContext generationContext) {
        NoiseColumn fartherColumnOfBlocks = generationContext.chunkGenerator().getBaseColumn(initialSupport.getBoundingBox().maxX(), initialSupport.getBoundingBox().maxZ(), generationContext.heightAccessor(), generationContext.randomState());
        int fartherLandHeight = generationContext.chunkGenerator().getFirstOccupiedHeight(positionX, positionZ, Heightmap.Types.WORLD_SURFACE_WG, generationContext.heightAccessor(), generationContext.randomState());
        if(fartherLandHeight < initialSupport.getBoundingBox().minY()) {
            return false;
        }

        BlockState fartherBlockOnGround = fartherColumnOfBlocks.getBlock(fartherLandHeight);

        if(!isBlockValidForStructure(fartherBlockOnGround)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a given block is valid for spawning the structure when given its block state.
     *
     * @param blockState State of the block we are trying to spawn the structure over.
     * @return Whether the block is valid for spawning this structure or not
     */
    public static boolean isBlockValidForStructure(BlockState blockState) {
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        }

        // TODO: Would be better to check for the "replaceable plants" tag instead so that it doesnt spawn on flowers and such either
        if (blockState.is(Blocks.GRASS)) {
            return false;
        }

        return blockState.getTags().anyMatch((element) -> element != BlockTags.REPLACEABLE_BY_TREES);
    }

    /**
     * Inner class that represents an individual piece of the structure
     */
    public static class Piece extends TemplateStructurePiece {
        public Piece(StructureTemplateManager manager, ResourceLocation resourceLocation, BlockPos position, Rotation rotation, int offsetY) {
            super(ModStructurePieceTypes.TORII_GATE_PIECE, 0, manager, resourceLocation, resourceLocation.toString(), loadTemplate(manager, resourceLocation, rotation), position.offset(OFFSETS.get(resourceLocation)).below(offsetY));
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
