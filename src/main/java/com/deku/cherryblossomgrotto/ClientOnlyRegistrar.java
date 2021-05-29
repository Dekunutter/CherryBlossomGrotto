package com.deku.cherryblossomgrotto;

import net.minecraftforge.eventbus.api.IEventBus;

public class ClientOnlyRegistrar {
    private IEventBus eventBus;

    public ClientOnlyRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Registers any events that must ONLY be registered on a client.
     * This may include things like graphics rendering, user input processing, etc.
     */
    public void registerClientOnlyEvents() {

    }
}
