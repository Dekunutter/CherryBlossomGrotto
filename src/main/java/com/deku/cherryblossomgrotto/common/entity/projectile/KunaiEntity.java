package com.deku.cherryblossomgrotto.common.entity.projectile;

import com.deku.cherryblossomgrotto.common.entity.ModEntityTypeInitializer;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.PlayMessages;

public class KunaiEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    public KunaiEntity(EntityType<KunaiEntity> entityType, Level level) {
        super(entityType, level);
        setBaseDamage(2.0D);
    }

    public KunaiEntity(LivingEntity livingEntity, Level level) {
        super(ModEntityTypeInitializer.KUNAI_ENTITY_TYPE.get(), livingEntity, level);
        setBaseDamage(2.0D);
    }

    public KunaiEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(ModEntityTypeInitializer.KUNAI_ENTITY_TYPE.get(), level);
        setBaseDamage(2.0D);
    }

    /**
     * Gets the item that is picked up when this entity is collected
     *
     * @return The item stack that is added to a living entities inventory when picked up
     */
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.KUNAI);
    }

    /**
     * Overrides the network hooks for the entity spawning packets so that the server can communicate to the client
     * that an entity was spawned into the world.
     *
     * @return The network packet for spawning the entity
     */
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    /**
     * Writes spawning data to an NBT so that the entity instance can read custom data over the network
     *
     * @param buffer The buffer for writing the NBT data to the network packet
     */
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag compoundNBT = new CompoundTag();
        buffer.writeNbt(compoundNBT);
    }

    /**
     * Reads spawning data from the NBT contained within the network packet for this entity instance
     *
     * @param additionalData NBT data stored in a network packet buffer
     */
    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag compoundNBT = additionalData.readNbt();
    }

    /**
     * Gets the default sound event that plays when this entity hits the ground
     *
     * @return The sound event to be played when colliding with a block
     */
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.CHAIN_HIT;
    }

    /**
     * Logic that plays out whenever this entity collides with a block.
     * This overrides the shake time on the abstract arrow entity so that our entity shakes less on collision.
     * It then ensures that our sound event is not overridden by the standard arrow sound for subsequent events.
     *
     * @param rayTraceResult Result of the ray trace to the colliding block
     */
    @Override
    protected void onHitBlock(BlockHitResult rayTraceResult) {
        super.onHitBlock(rayTraceResult);
        shakeTime = 3;
        setSoundEvent(SoundEvents.CHAIN_HIT);
    }
}
