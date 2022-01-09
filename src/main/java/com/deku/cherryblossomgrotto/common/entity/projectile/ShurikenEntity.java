package com.deku.cherryblossomgrotto.common.entity.projectile;

import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class ShurikenEntity extends AbstractArrowEntity implements IEntityAdditionalSpawnData {
    public float spin = 0.0f;

    public ShurikenEntity(EntityType<Entity> entityType, World world) {
        super(ModEntityData.SHURIKEN_DATA, world);
    }

    public ShurikenEntity(LivingEntity livingEntity, World world) {
        super(ModEntityData.SHURIKEN_DATA, livingEntity, world);
        setBaseDamage(0.1D);
    }

    public ShurikenEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(ModEntityData.SHURIKEN_DATA, world);
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Writes spawning data to an NBT so that the entity instance can read custom data over the network
     *
     * @param buffer The buffer for writing the NBT data to the network packet
     */
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        CompoundNBT compoundNBT = new CompoundNBT();
        buffer.writeNbt(compoundNBT);
    }

    /**
     * Reads spawning data from the NBT contained within the network packet for this entity instance
     *
     * @param additionalData NBT data stored in a network packet buffer
     */
    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        CompoundNBT compoundNBT = additionalData.readNbt();
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
    protected void onHitBlock(BlockRayTraceResult rayTraceResult) {
        super.onHitBlock(rayTraceResult);
        shakeTime = 0;
        this.setSoundEvent(SoundEvents.CHAIN_HIT);
    }
}
