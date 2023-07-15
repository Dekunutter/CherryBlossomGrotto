package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class ShiitakeMushroom extends MushroomBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SOUTH_SHAPE = Block.box(5.0D, 6.0D, 0.0D, 11.0D, 13.0D, 7.0D);
    protected static final VoxelShape NORTH_SHAPE = Block.box(5.0D, 6.0D, 9.0D, 11.0D, 13.0D, 16.0D);
    protected static final VoxelShape WEST_SHAPE = Block.box(9.0D, 6.0D, 5.0D, 16.0D, 13.0D, 11.0D);
    protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 6.0D, 5.0D, 7.0D, 13.0D, 11.0D);

    protected static final Direction[] DIRECTIONS = Direction.values();

    // TODO: Just make this protected with an accesstransformer. Just adding here as a quick work around for the time being
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public ShiitakeMushroom() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(ShiitakeMushroom::always).pushReaction(PushReaction.DESTROY), ModTreeFeatures.HUGE_SHIITAKE_MUSHROOM);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));

        // TODO: Temp, see above
        this.feature = ModTreeFeatures.HUGE_SHIITAKE_MUSHROOM;
    }

    private static Boolean always(BlockState state, BlockGetter getter, BlockPos position) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos position, CollisionContext collisionContext) {
        Direction direction = state.getValue(FACING);
        switch(direction) {
            case EAST:
            default:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
        }
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState otherBlockState, LevelAccessor levelAccessor, BlockPos position, BlockPos otherPosition) {
        return !blockState.canSurvive(levelAccessor, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, otherBlockState, levelAccessor, position, otherPosition);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = blockPlaceContext.getLevel();
        BlockPos blockpos = blockPlaceContext.getClickedPos();

        Direction direction = blockPlaceContext.getClickedFace();
        if (direction.getAxis().isHorizontal()) {
            blockstate = blockstate.setValue(FACING, direction);
            if (blockstate.canSurvive(levelreader, blockpos) && this.canPlace(blockstate, levelreader, blockpos)) {
                return blockstate;
            }
        }

        return null;
    }

    public boolean canPlace(BlockState state, LevelReader levelReader, BlockPos position) {
        Direction direction = state.getValue(FACING);
        Direction rootDirection = direction.getOpposite();
        return this.canAttachTo(levelReader, state, position.relative(rootDirection), direction);
    }

    public boolean canAttachTo(LevelReader p_249746_, BlockState p_251128_, BlockPos p_250583_, Direction p_250567_) {
        BlockState blockstate = p_249746_.getBlockState(p_250583_);
        return blockstate.isFaceSturdy(p_249746_, p_250583_, p_250567_, SupportType.FULL);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos position) {
        return canPlace(state, levelReader, position);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public boolean growMushroom(ServerLevel level, BlockPos position, BlockState state, RandomSource random) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
        if (optional.isEmpty()) {
            return false;
        } else {
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(level, random, position, optional.get());
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) return false;
            //level.removeBlock(position, false);
            if (event.getFeature().value().place(level, level.getChunkSource().getGenerator(), random, position)) {
                return true;
            } else {
                // Remove the mushroom from the world AFTER placement instead of BEFORE so that I can grab its state during generation
                level.setBlock(position, state, 3);
                level.removeBlock(position, false);
                return false;
            }
        }
    }
}
