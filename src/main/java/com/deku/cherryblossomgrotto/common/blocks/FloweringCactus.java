package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class FloweringCactus extends Block implements net.minecraftforge.common.IPlantable {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    public FloweringCactus() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.4F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY));
    }

    public VoxelShape getCollisionShape(BlockState p_51176_, BlockGetter p_51177_, BlockPos p_51178_, CollisionContext p_51179_) {
        return COLLISION_SHAPE;
    }

    public VoxelShape getShape(BlockState p_51176_, BlockGetter p_51177_, BlockPos p_51178_, CollisionContext p_51179_) {
        return COLLISION_SHAPE;
    }

    public BlockState updateShape(BlockState p_51157_, Direction p_51158_, BlockState p_51159_, LevelAccessor p_51160_, BlockPos p_51161_, BlockPos p_51162_) {
        if (!p_51157_.canSurvive(p_51160_, p_51161_)) {
            p_51160_.scheduleTick(p_51161_, this, 1);
        }

        return super.updateShape(p_51157_, p_51158_, p_51159_, p_51160_, p_51161_, p_51162_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter blockGetter, BlockPos position, Direction facing, IPlantable plantable) {
        return state.is(BlockTags.SAND);
    }

    public boolean canSurvive(BlockState p_51153_, LevelReader p_51154_, BlockPos p_51155_) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = p_51154_.getBlockState(p_51155_.relative(direction));
            if (blockstate.isSolid() || p_51154_.getFluidState(p_51155_.relative(direction)).is(FluidTags.LAVA)) {
                return false;
            }
        }

        BlockState blockstate1 = p_51154_.getBlockState(p_51155_.below());
        return blockstate1.canSustainPlant(p_51154_, p_51155_, Direction.UP, this) && !p_51154_.getBlockState(p_51155_.above()).liquid();
    }

    public void entityInside(BlockState p_51148_, Level p_51149_, BlockPos p_51150_, Entity p_51151_) {
        p_51151_.hurt(p_51149_.damageSources().cactus(), 1.0F);
    }

    public boolean isPathfindable(BlockState p_51143_, BlockGetter p_51144_, BlockPos p_51145_, PathComputationType p_51146_) {
        return false;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.DESERT;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }
}
