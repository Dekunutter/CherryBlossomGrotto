package com.deku.eastwardjourneys.common.entity.projectile;

import com.deku.eastwardjourneys.common.entity.ModEntityTypeInitializer;
import com.deku.eastwardjourneys.common.items.ModItems;
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

public class ShurikenEntity extends AbstractArrow implements IEntityAdditionalSpawnData {
    public float spin = 0.0f;

    public ShurikenEntity(EntityType<ShurikenEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ShurikenEntity(LivingEntity livingEntity, Level level) {
        super(ModEntityTypeInitializer.SHURIKEN_ENTITY_TYPE.get(), livingEntity, level);
        setBaseDamage(0.1D);
    }

    public ShurikenEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(ModEntityTypeInitializer.SHURIKEN_ENTITY_TYPE.get(), level);
        setBaseDamage(0.1D);
    }

    /**
     * Gets the item that is picked up when this entity is collected
     *
     * @return The item stack that is added to a living entities inventory when picked up
     */
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.SHURIKEN);
    }

    /**
     * Overrides the network hooks for the entity spawning packets so that the server can communicate to the client
     * that an entity was spawned into the world.
     *
     * @return The network packet for spawning the entity
     */
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
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
     * Logic that is run on each tick of the world to update this entity instance.
     * Controls all flight-path information on the entity as it glides through the air and then applies
     * an arbitrary spin on it as long as it is not grounded, for renderering purposes.
     */
    @Override
    public void tick() {
        super.tick();

        if (!inGround) {
            spin += 100;
        }
    }

    /**
     * Logic that plays out whenever this entity collides with a block.
     * This overrides the shake time on the abstract arrow entity so that our entity never shakes on collision.
     * It then ensures that our sound event is not overridden by the standard arrow sound for subsequent events.
     *
     * @param rayTraceResult Result of the ray trace to the colliding block
     */
    @Override
    protected void onHitBlock(BlockHitResult rayTraceResult) {
        super.onHitBlock(rayTraceResult);
        shakeTime = 0;
        this.setSoundEvent(SoundEvents.CHAIN_HIT);
    }
}
