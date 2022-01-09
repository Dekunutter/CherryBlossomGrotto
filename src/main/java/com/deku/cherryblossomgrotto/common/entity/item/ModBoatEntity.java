package com.deku.cherryblossomgrotto.common.entity.item;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.entity.ModEntityData;
import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class ModBoatEntity extends BoatEntity {
    private static final DataParameter<Integer> MOD_BOAT_TYPE = EntityDataManager.defineId(ModBoatEntity.class, DataSerializers.INT);

    public ModBoatEntity(EntityType<Entity> entityType, World world) {
        super(ModEntityData.MOD_BOAT_DATA, world);
    }

    public ModBoatEntity(World world, double positionX, double positionY, double positionZ) {
        super(ModEntityData.MOD_BOAT_DATA, world);
        this.setPos(positionX, positionY, positionZ);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = positionX;
        this.yo = positionY;
        this.zo = positionZ;

    }

    public ModBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super(ModEntityData.MOD_BOAT_DATA, world);
    }

    /**
     * Ensures that our custom boat type is added as additional save data to any modded boat instance
     *
     * @param compound The object the custom boat type is being saved to
     */
    @Override
    protected void addAdditionalSaveData(CompoundNBT compound)
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
    protected void readAdditionalSaveData(CompoundNBT compound)
    {
        super.readAdditionalSaveData(compound);

        if (compound.contains("ModType", 8))
            this.setModBoatType(ModBoatEntity.ModType.byName(compound.getString("ModType")));
    }

    /**
     * Defines what data should be synced with the server.
     * Adds our custom boat type as data that the server needs to know about.
     */
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(MOD_BOAT_TYPE, ModBoatEntity.ModType.CHERRY.ordinal());
    }

    /**
     * Gets the entity spawning packet
     *
     * @return The packet containing entity spawning data to be sent across the network
     */
    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Sets the type of the boat using our custom modded boat type
     *
     * @param type The mod type we want to assign this boat
     */
    public void setModBoatType(ModBoatEntity.ModType type) {
        this.entityData.set(MOD_BOAT_TYPE, type.ordinal());
    }

    /**
     * Gets the current type of the boat according to our custom modded boat type
     *
     * @return The mod type currently assigned to this boat
     */
    public ModBoatEntity.ModType getModBoatType() {
        return ModBoatEntity.ModType.byId(this.entityData.get(MOD_BOAT_TYPE));
    }

    /**
     * Ensures that any attempt to get the vanilla boat type returns a consistent value.
     * Returning the oak boat type as a standard.
     * Just a safety net in case of missed calls to our modded boat type and since this type data cannot be extended
     * and needs to be set to something anyway
     *
     * @return The vanilla boat type assigned to this boat
     */
    @Override
    public BoatEntity.Type getBoatType()
    {
        return BoatEntity.Type.OAK;
    }

    /**
     * Ensures that we can't change the vanilla boat type
     *
     * @param boatType The boat type we would be changing to, if we allowed it.
     */
    @Override
    public void setType(BoatEntity.Type boatType)
    {
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

    /**
     * An enum containing possible boat types associated with this mod.
     * We use a new type since the types that are possible in vanilla cannot be overridden or extended directly.
     */
    public enum ModType {
        CHERRY(ModBlocks.CHERRY_PLANKS, "cherry_blossom");

        private final String name;
        private final Block planks;

        ModType(Block planks, String name) {
            this.name = name;
            this.planks = planks;
        }

        /**
         * Gets the name of the boat type
         *
         * @return Name of the boat type
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets the planks block that corresponds to the same wood type of this boat
         *
         * @return The planks block of the same wood type as this boat
         */
        public Block getPlanks() {
            return this.planks;
        }

        /**
         * Converts this enum value to a string
         *
         * @return The name of the boat type
         */
        public String toString() {
            return this.name;
        }

        /**
         * Gets the enum value for this boat type by its ID
         *
         * @param id The ID we want the boat type for
         * @return The boat type with the given ID
         */
        public static ModBoatEntity.ModType byId(int id) {
            ModBoatEntity.ModType[] boatEntityType = values();
            if (id < 0 || id >= boatEntityType.length) {
                id = 0;
            }

            return boatEntityType[id];
        }

        /**
         * Gets the enum value for this boat type by its name
         *
         * @param name The name of the boat type we want
         * @return The boat type with the given name
         */
        public static ModBoatEntity.ModType byName(String name) {
            ModBoatEntity.ModType[] boatEntityType = values();

            for(int i = 0; i < boatEntityType.length; ++i) {
                if (boatEntityType[i].getName().equals(name)) {
                    return boatEntityType[i];
                }
            }

            return boatEntityType[0];
        }
    }
}
