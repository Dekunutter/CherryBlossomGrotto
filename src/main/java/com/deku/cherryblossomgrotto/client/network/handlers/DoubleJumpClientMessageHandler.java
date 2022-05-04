package com.deku.cherryblossomgrotto.client.network.handlers;

import com.deku.cherryblossomgrotto.Main;
import com.deku.cherryblossomgrotto.client.network.messages.DoubleJumpClientMessage;
import com.deku.cherryblossomgrotto.common.capabilities.DoubleJumpCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class DoubleJumpClientMessageHandler {
    /**
     * Handles a message containing information about a double jump performed by a player.
     * This checks that the message was received by the right side (client vs server), that the message contents
     * are valid, and that the world that this double jump event is happening in currently exists on this client.
     *
     * Assuming all of the above is true, the message is then processed by the network event context.
     *
     * @param message The message that was received by the client
     * @param contextSupplier The network event context supplier
     */
    public static void onMessageReceived(final DoubleJumpClientMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            Main.LOGGER.error("DoubleJumpClientMessage received on wrong side: " + context.getDirection().getReceptionSide());
            return;
        }
        if (!message.isValid()) {
            Main.LOGGER.error("DoubleJumpClientMessage was invalid: " + message.toString());
            return;
        }

        Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!level.isPresent()) {
            Main.LOGGER.error("DoubleJumpClientMessage context could not provide a client world");
            return;
        }

        context.enqueueWork(() -> processMessage(level.get(), message));
    }

    /**
     * Processing logic that happens on a double jump client message. This syncs the double jump event processed
     * by the server onto this client.
     *
     * This gets the player performing the double jump by extracting their UUID from the message, then it finds
     * their associated double jump capability and sets their double jump state to match the state passed in by
     * the message. This is then written to the client's double jump capability for this player as NBT data.
     *
     * @param level The level that the message was received for
     * @param message The message received by the client
     */
    private static void processMessage(Level level, DoubleJumpClientMessage message) {
        Player player = level.getPlayerByUUID(message.getPlayerId());
        DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DoubleJumpCapability.DOUBLE_JUMP).orElse(null);
        if (doubleJumpCapability != null) {
            doubleJumpCapability.setHasDoubleJumped(message.hasDoubleJumped());
        }
        // TODO: Render an effect here for jumping while in the air
    }

    /**
     * Checks if a given network protocol version is supported by this client
     *
     * @param protocolVersion The protocol version we want to check support for
     * @return Whether this network protocol is supported
     */
    public static boolean isProtocolAcceptedOnClient(String protocolVersion) {
        return "1.0".equals(protocolVersion);
    }
}
