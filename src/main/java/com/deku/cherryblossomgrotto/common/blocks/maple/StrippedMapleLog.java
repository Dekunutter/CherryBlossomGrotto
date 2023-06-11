package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HAS_MAPLE_SYRUP;

public class StrippedMapleLog extends AbstractWoodenBlock {
    public StrippedMapleLog() {
        super(Properties.of().strength(2.0f).mapColor(MapColor.WOOD).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
        registerDefaultState(defaultBlockState().setValue(HAS_MAPLE_SYRUP, true));
    }

    /**
     * Determines what should happen when the player interacts with this block.
     * In this case, if the player is holding an empty bottle and the block contains maple syrup,
     * we want to fill that bottle with the syrup and place it in the player's inventory.
     *
     * @param state The state of the block being interacted with
     * @param level The level containing the block being interacted with
     * @param position The position of the block being interacted with
     * @param player The player that is interacting with the block
     * @param hand The hand that is triggering the block interaction
     * @param hitResult The result of a ray trace hitting the block
     * @return The interaction result caused by the player interacting with this block
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);

        boolean hasSucceeded = false;
        if (itemStack.is(Items.GLASS_BOTTLE) && state.getValue(HAS_MAPLE_SYRUP)) {
            itemStack.shrink(1);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE));
            } else if (!player.getInventory().add(new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE))) {
                player.drop(new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE), false);
            }

            hasSucceeded = true;
            level.gameEvent(player, GameEvent.FLUID_PICKUP, position);
        }

        if (hasSucceeded) {
            level.setBlock(position, state.setValue(HAS_MAPLE_SYRUP, false), 3);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, position, player, hand, hitResult);
        }
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     * This allows us to denote whether the block contains maple syrup or not.
     *
     * @param builder The builder for the state container
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HAS_MAPLE_SYRUP);
    }
}
