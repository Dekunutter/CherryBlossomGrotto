package com.deku.cherryblossomgrotto.common.entity.vehicle;

import com.deku.cherryblossomgrotto.common.entity.ModEntityType;
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
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;

public class ModChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> MOD_CHEST_BOAT_TYPE = SynchedEntityData.defineId(ModChestBoatEntity.class, EntityDataSerializers.INT);

    public ModChestBoatEntity(EntityType<? extends Entity> entityType, Level level) {
        super(ModEntityType.MOD_CHEST_BOAT, level);
    }

    public ModChestBoatEntity(Level level, double positionX, double positionY, double positionZ) {
        super(ModEntityType.MOD_CHEST_BOAT, level);
        this.setPos(positionX, positionY, positionZ);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = positionX;
        this.yo = positionY;
        this.zo = positionZ;

    }

    public ModChestBoatEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(ModEntityType.MOD_CHEST_BOAT, level);
    }

    /**
     * Ensures that our custom chest boat type is added as additional save data to any modded boat instance
     *
     * @param compound The object the custom boat type is being saved to
     */
    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);

        compound.putString("ModType", this.getModChestBoatType().getName());
    }

    /**
     * Ensures that our custom chest boat type is read from save data when the object is an instance of our custom
     * boat.
     *
     * @param compound The object the custom boat type is being read from
     */
    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);

        if (compound.contains("ModType", 8))
            this.setModChestBoatType(ModBoatTypes.byName(compound.getString("ModType")));
    }

    /**
     * Defines what data should be synced with the server.
     * Adds our custom boat type as data that the server needs to know about.
     */
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(MOD_CHEST_BOAT_TYPE, ModBoatTypes.CHERRY.ordinal());
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
     * @param type The mod type we want to assign this chest boat
     */
    public void setModChestBoatType(ModBoatTypes type) {
        this.entityData.set(MOD_CHEST_BOAT_TYPE, type.ordinal());
    }

    /**
     * Gets the current type of the chest boat according to our custom modded boat type
     *
     * @return The mod type currently assigned to this chest boat
     */
    public ModBoatTypes getModChestBoatType() {
        return ModBoatTypes.byId(this.entityData.get(MOD_CHEST_BOAT_TYPE));
    }

    /**
     * Determines what item drops down this chest boat is broken
     *
     * @return The item to be dropped
     */
    @Override
    public Item getDropItem() {
        switch(this.getModChestBoatType()) {
            case CHERRY:
            default:
                return ModItems.CHERRY_BOAT;
        }
    }
}
