package org.ourproject.kune.app.server.wave;

import com.google.inject.AbstractModule;

public class WaveStarterModule extends AbstractModule {

    private final CustomServerRpcProviderSamePort rpc;

    public WaveStarterModule(CustomServerRpcProviderSamePort rpc) {
        this.rpc = rpc;
    }

    @Override
    protected void configure() {
        install(rpc);
        // rpc.startWebSocketServer();
    }
}
