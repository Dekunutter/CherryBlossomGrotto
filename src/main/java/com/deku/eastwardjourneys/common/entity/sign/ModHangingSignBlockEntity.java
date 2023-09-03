package com.deku.eastwardjourneys.common.entity.sign;

import com.deku.eastwardjourneys.common.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignBlockEntity extends HangingSignBlockEntity {

    public ModHangingSignBlockEntity(BlockPos position, BlockState blockState) {
        super(position, blockState);
    }

    /**
     * Gets the type for the sign block
     *
     * @return Sign block type
     */
    @Override
    public BlockEntityType<ModHangingSignBlockEntity> getType() {
        return ModBlockEntities.HANGING_SIGN_ENTITY_TYPE;
    }
}
