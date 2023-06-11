package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.blockEntities.MapleLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.entity.sign.ModHangingSignBlockEntity;
import com.deku.cherryblossomgrotto.common.entity.sign.ModSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static BlockEntityType<MapleLeavesBlockEntity> MAPLE_LEAVES_TYPE = BlockEntityType.Builder.of(MapleLeavesBlockEntity::new, ModBlocks.MAPLE_LEAVES).build(null);

    public static BlockEntityType<ModSignBlockEntity> SIGN_ENTITY_TYPE = BlockEntityType.Builder.of(ModSignBlockEntity::new, ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN, ModBlocks.BLACK_PINE_SIGN, ModBlocks.BLACK_PINE_WALL_SIGN).build(null);

    public static BlockEntityType<ModHangingSignBlockEntity> HANGING_SIGN_ENTITY_TYPE = BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN, ModBlocks.BLACK_PINE_HANGING_SIGN, ModBlocks.BLACK_PINE_WALL_HANGING_SIGN).build(null);
}
