package com.deku.eastwardjourneys.server.network.messages;

import com.deku.eastwardjourneys.Main;
import net.minecraft.network.FriendlyByteBuf;

public class DoubleJumpServerMessage {
    public static final int MESSAGE_ID = 35;

    private boolean hasDoubleJumped;
    private boolean isValid;

    private DoubleJumpServerMessage() {
        isValid = false;
    }

    public DoubleJumpServerMessage(boolean hasDoubleJumped) {
        this.hasDoubleJumped = hasDoubleJumped;
        isValid = true;
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
     * Gets the validity of this message
     *
     * @return Whether this message can be considered valid
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Decodes this message from the network packet buffer into a full server message object.
     * If we fail to decode a message from the packet buffer we will return what we managed to decode and
     * mark the message as invalid.
     *
     * @param buffer The buffer for reading network packet information into a message object
     * @return The server message decoded from the packet buffer
     */
    public static DoubleJumpServerMessage decode(FriendlyByteBuf buffer) {
        DoubleJumpServerMessage message = new DoubleJumpServerMessage();
        try {
            message.hasDoubleJumped = buffer.readBoolean();
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            Main.LOGGER.error("Exception occurred while attempting to decode server double jump network message: " + ex);
            return message;
        }

        message.isValid = true;
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

        buffer.writeBoolean(hasDoubleJumped);
    }

    /**
     * Converts this message object to a readable string
     *
     * @return String containing information on this server message
     */
    @Override
    public String toString() {
        return "DoubleJumpServerMessage[hasDoubleJumped=" + String.valueOf(hasDoubleJumped) + "]";
    }
}
