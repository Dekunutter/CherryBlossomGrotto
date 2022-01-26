package com.deku.cherryblossomgrotto.common.capabilities;

import com.deku.cherryblossomgrotto.Main;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.deku.cherryblossomgrotto.common.capabilities.ModCapabilitiesInitializer.DOUBLE_JUMP_CAPABILITY;

public class DoubleJumpCapability implements ICapabilitySerializable<CompoundNBT> {
    private DoubleJump doubleJump = createDefaultInstance();

    /**
     * Creates a default instance for this capabilities.
     * This will be used whenever the player writes or reads any double jump capability information so that
     * information can persist.
     *
     * @return The default instance of this capability.
     */
    public static DoubleJump createDefaultInstance() {
        return new DoubleJump();
    }

    /**
     * Serialize the NBT data on this capability
     *
     * @return The compound NBT storing this capability's data
     */
    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) DOUBLE_JUMP_CAPABILITY.writeNBT(doubleJump, null);
    }

    /**
     * Deserialize the NBT data for this capability
     *
     * @param compoundNBT The compound NBT holding all of this capability's data
     */
    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        DOUBLE_JUMP_CAPABILITY.readNBT(doubleJump, null, compoundNBT);
    }

    /**
     * Gets this double jump capability.
     * This variation of the getter ensures that this capability is agnostic to any directional
     * information.
     *
     * @param capability The capability we are getting
     * @param side The direction of this capability
     * @param <T> Generic for this lazy optional
     * @return Lazy optional containing our default capability instance
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return (LazyOptional<T>) LazyOptional.of(() -> doubleJump);
    }

    /**
     * Gets this double jump capability.
     *
     * @param capability The capability we are getting
     * @param <T> Generic for this lazy optional
     * @return Lazy optional containing our default capability instance
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability) {
        return ICapabilitySerializable.super.getCapability(capability);
    }

    /**
     * Inner interface for defining the setters and getters needed on our capability
     */
    public interface IDoubleJump {
        boolean hasDoubleJumped();
        void setHasDoubleJumped(boolean value);
    }

    /**
     * Inner class which defines the information stored by our capability
     */
    public static class DoubleJump implements IDoubleJump {
        private boolean hasDoubleJumped = false;

        /**
         * Whehther the player has performed a double jump
         *
         * @return Whether the player has performed a double jump
         */
        public boolean hasDoubleJumped() {
            return hasDoubleJumped;
        }

        /**
         * Sets the double jumping state for the player
         *
         * @param value The double jumping state we are setting
         */
        public void setHasDoubleJumped(boolean value) {
            hasDoubleJumped = value;
        }
    }

    /**
     * Inner class which handles all the necessary functions for reading and writing the capability's data
     */
    public static class DoubleJumpStorage implements Capability.IStorage<IDoubleJump> {
        /**
         * Writes this capability's data to NBT
         *
         * @param capability Type of the capability at play here
         * @param instance Instance of the capability that we are reading the data from
         * @param side The directional information attached to this capability
         * @return NBT data of all our capability's information
         */
        @Nullable
        @Override
        public INBT writeNBT(Capability<IDoubleJump> capability, IDoubleJump instance, Direction side) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putBoolean("hasDoubleJumped", instance.hasDoubleJumped());
            return compoundNBT;
        }

        /**
         * Reads data from NBT to populate this capability's instance
         *
         * @param capability Type of the capability at play
         * @param instance Instance of the capability we want to populate with the information stored in NBT
         * @param side The directional information attached to this capability
         * @param nbt NBT that we are reading from
         */
        public void readNBT(Capability<IDoubleJump> capability, IDoubleJump instance, Direction side, INBT nbt) {
            if (instance instanceof DoubleJump) {
                CompoundNBT compoundNBT = (CompoundNBT) nbt;
                instance.setHasDoubleJumped(compoundNBT.getBoolean("hasDoubleJumped"));
            } else {
                throw new IllegalArgumentException("Cannot deserialize to an instance that isn't the default implementation");
            }
        }
    }
}
