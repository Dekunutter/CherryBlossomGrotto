package com.deku.eastwardjourneys.client.network.messages;

import com.deku.eastwardjourneys.Main;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class DoubleJumpClientMessage {
    public static final int MESSAGE_ID = 63;

    private UUID playerId;
    private boolean hasDoubleJumped;
    private boolean isValid;

    public DoubleJumpClientMessage() {
        playerId = null;
        hasDoubleJumped = false;
        isValid = false;
    }

    public DoubleJumpClientMessage(UUID playerId, boolean hasDoubleJumped) {
        this.playerId = playerId;
        this.hasDoubleJumped = hasDoubleJumped;
        isValid = true;
    }

    /**
     * Gets the UUID of the player that this message is intended for
     *
     * @return UUID of the player
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Sets the UUID of the player that this message is intended for
     *
     * @param id UUID of a player that we want to associate this message with
     */
    public void setPlayerId(UUID id) {
        playerId = id;
    }

    /**
     * Checks if this message is communicating a performed double jump
     *
     * @return Whether this message is communicating that a double jump was performed
     */
    public boolean hasDoubleJumped() {
        return hasDoubleJumped;
    }

    /**
     * Sets the double jump state on this message
     *
     * @param value The state we want to set the double jump check to on this message
     */
    public void setHasDoubleJumped(boolean value) {
        hasDoubleJumped = value;
    }

    /**
     * Gets the validity of this message
     *
     * @return Whether this message can be considered valid
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Sets the validity of this message
     *
     * @param value Whether this message is to be marked as valid or not
     */
    public void setIsValid(boolean value) {
        isValid = value;
    }

    /**
     * Decodes this message from the network packet buffer into a full client message object.
     * If we fail to decode a message from the packet buffer we will return what we managed to decode and
     * mark the message as invalid.
     *
     * @param buffer The buffer for reading network packet information into a message object
     * @return The client message decoded from the packet buffer
     */
    public static DoubleJumpClientMessage decode(FriendlyByteBuf buffer) {
        DoubleJumpClientMessage message = new DoubleJumpClientMessage();
        try {
            UUID playerId = buffer.readUUID();
            boolean hasDoubleJumped = buffer.readBoolean();

            message.setPlayerId(playerId);
            message.setHasDoubleJumped(hasDoubleJumped);
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            Main.LOGGER.error("Exception occurred while trying to decode client double jump network message: " + ex);
            return message;
        }

        message.setIsValid(true);
        return message;
    }

    /**
     * Encodes the current message instance into a network packet buffer
     *
     * @param buffer The network packet buffer we are writing this message's instance values to
     */
    public void encode(FriendlyByteBuf buffer) {
        if (!isValid) {
            return;
        }

        buffer.writeUUID(playerId);
        buffer.writeBoolean(hasDoubleJumped);
    }

    /**
     * Converts this message object to a readable string
     *
     * @return String containing information on this client message
     */
    @Override
    public String toString() {
        return "DoubleJumpClientMessage[hasDoubleJumped=" + playerId.toString() + ", " + String.valueOf(hasDoubleJumped) + "]";
    }
}
