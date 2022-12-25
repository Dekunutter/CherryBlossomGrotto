package com.deku.cherryblossomgrotto.common.entity.vehicle;

import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> MOD_BOAT_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);

    public ModBoatEntity(EntityType<? extends Entity> entityType, Level level) {
        super(ModEntityData.MOD_BOAT_DATA, level);
    }

    public ModBoatEntity(Level level, double positionX, double positionY, double positionZ) {
        super(ModEntityData.MOD_BOAT_DATA, level);
        this.setPos(positionX, positionY, positionZ);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = positionX;
        this.yo = positionY;
        this.zo = positionZ;

    }

    public ModBoatEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(ModEntityData.MOD_BOAT_DATA, level);
    }

    /**
     * Ensures that our custom boat type is added as additional save data to any modded boat instance
     *
     * @param compound The object the custom boat type is being saved to
     */
    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);

        compound.putString("ModType", this.getModBoatType().getName());
    }

    /**
     * Ensures that our custom boat type is read from save data when the object is an instance of our custom
     * boat.
     *
     * @param compound The object the custom boat type is being read from
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);

        if (compound.contains("ModType", 8))
            this.setModBoatType(ModBoatTypes.byName(compound.getString("ModType")));
    }

    /**
     * Defines what data should be synced with the server.
     * Adds our custom boat type as data that the server needs to know about.
     */
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(MOD_BOAT_TYPE, ModBoatTypes.CHERRY.ordinal());
    }

    /**
     * Gets the entity spawning packet
     *
     * @return The packet containing entity spawning data to be sent across the network
     */
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return new ClientboundAddEntityPacket(this);
    }

    /**
     * Sets the type of the boat using our custom modded boat type
     *
     * @param type The mod type we want to assign this boat
     */
    public void setModBoatType(ModBoatTypes type) {
        this.entityData.set(MOD_BOAT_TYPE, type.ordinal());
    }

    /**
     * Gets the current type of the boat according to our custom modded boat type
     *
     * @return The mod type currently assigned to this boat
     */
    public ModBoatTypes getModBoatType() {
        return ModBoatTypes.byId(this.entityData.get(MOD_BOAT_TYPE));
    }

    /**
     * Determines what item drops down this boat is broken
     *
     * @return The item to be dropped
     */
    @Override
    public Item getDropItem() {
        switch(this.getModBoatType()) {
            case CHERRY:
            default:
                return ModItems.CHERRY_BOAT;
        }
    }
}
