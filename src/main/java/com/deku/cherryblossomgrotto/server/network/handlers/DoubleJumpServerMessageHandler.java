package com.deku.cherryblossomgrotto.server.network.handlers;

import com.deku.cherryblossomgrotto.Main;
import com.deku.cherryblossomgrotto.client.network.messages.DoubleJumpClientMessage;
import com.deku.cherryblossomgrotto.common.capabilities.DoubleJumpCapability;
import com.deku.cherryblossomgrotto.server.network.messages.DoubleJumpServerMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

import static com.deku.cherryblossomgrotto.common.capabilities.ModCapabilitiesInitializer.DOUBLE_JUMP_CAPABILITY;

public class DoubleJumpServerMessageHandler {
    /**
     * Handles a message containing information about a double jump performed by a player.
     * This checks that the message was received by the right side (server vs client), that the message contents are
     * valid, and that the network event was triggered by an actual player on this server.
     *
     * Assuming all of the above is true, the message is then processed by the network event context.
     *
     * @param message The message that was received by the server
     * @param contextSupplier The network event context supplier
     */
    public static void onMessageReceived(final DoubleJumpServerMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            Main.LOGGER.error("DoubleJumpServerMessage received on wrong side: " + context.getDirection().getReceptionSide());
            return;
        }
        if (!message.isValid()) {
            Main.LOGGER.error("DoubleJumpServerMessge was invalid: " + message);
            return;
        }

        final ServerPlayerEntity sendingPlayer = context.getSender();
        if (sendingPlayer == null) {
            Main.LOGGER.error("Sending player was null when double jump message was received");
        }

        context.enqueueWork(() -> processMessage(message, sendingPlayer));
    }

    /**
     * Processing logic that happens on a double jump server message. This formulates a client message for the given
     * player and sends it on to their client so that they can be synced with the server. A capability is populated
     * server-side as well since this will be needed for the server to sync info with the clients on certain events
     * like re-logging
     *
     * This only syncs players within the same dimension that the double jump action was performed in.
     *
     * @param message
     * @param player
     */
    public static void processMessage(DoubleJumpServerMessage message, ServerPlayerEntity player) {
        DoubleJumpClientMessage clientMessage = new DoubleJumpClientMessage(player.getUUID(), message.hasDoubleJumped());
        RegistryKey<World> playerDimension = player.getCommandSenderWorld().dimension();

        DoubleJumpCapability.IDoubleJump doubleJumpCapability = player.getCapability(DOUBLE_JUMP_CAPABILITY).orElse(null);
        if (doubleJumpCapability != null) {
            doubleJumpCapability.setHasDoubleJumped(message.hasDoubleJumped());
        }

        Main.NETWORK_CHANNEL.send(PacketDistributor.DIMENSION.with(() -> playerDimension), clientMessage);
    }

    /**
     * Checks if a given network protocol version is supported by this server
     *
     * @param protocolVersion The protocol version we want to check support for
     * @return Whether this network protocol is supported
     */
    public static boolean isProtocolAcceptedOnServer(String protocolVersion) {
        return Main.NETWORK_PROTOCOL_VERSION.equals(protocolVersion);
    }
}
