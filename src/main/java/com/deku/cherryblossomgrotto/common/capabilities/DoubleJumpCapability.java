package com.deku.cherryblossomgrotto.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DoubleJumpCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<DoubleJump> DOUBLE_JUMP = CapabilityManager.get(new CapabilityToken<>() {
    });

    private DoubleJump doubleJump = createDefaultInstance();

    private String DOUBLE_JUMP_STORAGE_NAME = "hasDoubleJumped";

    /**
     * Creates a default instance for this capabilities.
     * This will be used whenever the player writes or reads any double jump capability information so that
     * information can persist.
     *
     * @return The default instance of this capability.
     */
    @Nonnull
    public static DoubleJump createDefaultInstance() {
        return new DoubleJump();
    }

    /**
     * Serialize the NBT data on this capability
     *
     * @return The compound NBT storing this capability's data
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean(DOUBLE_JUMP_STORAGE_NAME, doubleJump.hasDoubleJumped());
        return nbt;
    }

    /**
     * Deserialize the NBT data for this capability
     *
     * @param compoundNBT The compound NBT holding all of this capability's data
     */
    @Override
    public void deserializeNBT(CompoundTag compoundNBT) {
        doubleJump.setHasDoubleJumped(compoundNBT.getBoolean(DOUBLE_JUMP_STORAGE_NAME));
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
        return getCapability(capability);
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
        return (LazyOptional<T>) LazyOptional.of(() -> doubleJump);

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
}
