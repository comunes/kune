package org.ourproject.kune.app.server.wave;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ourproject.kune.rack.ContainerListener;
import org.waveprotocol.wave.examples.fedone.federation.xmpp.WaveXmppComponent;
import org.waveprotocol.wave.examples.fedone.rpc.ServerRpcProvider;
import org.waveprotocol.wave.examples.fedone.waveserver.WaveClientRpc.ProtocolWaveClientRpc;
import org.xmpp.component.ComponentException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class WaveContainerListener implements ContainerListener {

    /** Creates InetSocketAddresses from injected parameters */
    public static class RpcInetSocketAddressFactory {

        private final String host;
        private final Integer port;

        @Inject
        RpcInetSocketAddressFactory(@Named("client_frontend_hostname") final String host,
                @Named("client_frontend_port") final Integer port) {
            this.host = host;
            this.port = port;
            // LOG.info("Starting client frontend on host: " + host + " port: "
            // + port);
        }

        InetSocketAddress create() {
            return new InetSocketAddress(host, port);
        }

    }
    private final WaveXmppComponent wxComponent;
    private final ProtocolWaveClientRpc.Interface rpcImpl;
    private final RpcInetSocketAddressFactory rpcInet;
    private final Logger log; // NOPMD by vjrj on 30/08/09 20:14

    @Inject
    public WaveContainerListener(final WaveXmppComponent wxComponent, final ProtocolWaveClientRpc.Interface rpcImpl,
            final RpcInetSocketAddressFactory rpcInet, final Logger logger) {
        this.rpcInet = rpcInet;
        this.wxComponent = wxComponent;
        this.rpcImpl = rpcImpl;
        this.log = logger;
    }

    public void start() {
        final ServerRpcProvider server = new ServerRpcProvider(rpcInet.create());
        server.registerService(ProtocolWaveClientRpc.newReflectiveService(rpcImpl));
        try {
            wxComponent.run();
        } catch (final ComponentException e) {
            log.log(Level.SEVERE, "couldn't connect to XMPP server:" + e);
        }
        log.log(Level.INFO, "Starting wave server");
        try {
            server.startServer();
        } catch (final Exception e) {
            throw new RuntimeException(e); // NOPMD by vjrj on 30/08/09 18:08
        }
    }

    public void stop() {
    }
}
