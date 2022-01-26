package com.deku.cherryblossomgrotto.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilitiesInitializer {
    @CapabilityInject(DoubleJumpCapability.IDoubleJump.class)
    public static Capability<DoubleJumpCapability.IDoubleJump> DOUBLE_JUMP_CAPABILITY = null;

    /**
     * Registers all capbilities by using the capability manager.
     */
    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(DoubleJumpCapability.IDoubleJump.class, new DoubleJumpCapability.DoubleJumpStorage(), DoubleJumpCapability::createDefaultInstance);
    }
}
