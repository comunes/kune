/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ourproject.kune.app.server.wave;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticate;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticationResult;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.rpc.MessageExpectingChannel;
import org.waveprotocol.box.server.rpc.ProtoCallback;
import org.waveprotocol.box.server.rpc.Rpc;
import org.waveprotocol.box.server.rpc.ServerRpcController;
import org.waveprotocol.box.server.rpc.SocketIOServerChannel;
import org.waveprotocol.box.server.rpc.WebSocketServerChannel;
import org.waveprotocol.box.server.util.NetUtils;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.util.logging.Log;

import com.glines.socketio.server.SocketIOInbound;
import com.glines.socketio.server.SocketIOServlet;
import com.glines.socketio.server.transport.FlashSocketTransport;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.servlet.ServletModule;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.google.protobuf.UnknownFieldSet;

/**
 * ServerRpcProvider can provide instances of type Service over an incoming
 * network socket and service incoming RPCs to these services and their methods.
 * 
 * 
 */
public class CustomServerRpcProviderSamePort extends ServletModule {
    private static final Log LOG = Log.get(CustomServerRpcProviderSamePort.class);

    private final InetSocketAddress[] httpAddresses;
    private final Integer flashsocketPolicyPort;
    private final Set<Connection> incomingConnections = Sets.newHashSet();
    private final ExecutorService threadPool;
    private final SessionManager sessionManager;
    private final org.eclipse.jetty.server.SessionManager jettySessionManager;
    // private Server httpServer = null;

    // Mapping from incoming protocol buffer type -> specific handler.
    private final Map<Descriptors.Descriptor, RegisteredServiceMethod> registeredServices = Maps.newHashMap();

    /**
     * Internal, static container class for any specific registered service
     * method.
     */
    static class RegisteredServiceMethod {
        final Service service;
        final MethodDescriptor method;

        RegisteredServiceMethod(Service service, MethodDescriptor method) {
            this.service = service;
            this.method = method;
        }
    }

    // SocketIO is a Generic Servlet then we use this workaround
    // http://stackoverflow.com/questions/3597414/how-to-integrate-hessian-with-guice
    @Singleton
    public class WaveSocketIOWrapperServlet extends HttpServlet {

        private WaveSocketIOServlet waveSocketIOServlet;

        @Override
        public void init(ServletConfig config) throws ServletException {
            LOG.info("init() in");
            try {
                if (waveSocketIOServlet == null) {
                    waveSocketIOServlet = new WaveSocketIOServlet();
                }
                waveSocketIOServlet.init(config);
            } catch (Throwable t) {
                LOG.severe("Error initialising waveSocketIO servlet", t);
                throw new ServletException(t);
            }
            LOG.info("init() out");
        }

        @Override
        public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

            try {
                waveSocketIOServlet.service(request, response);
            } catch (Throwable t) {
                LOG.severe("Error calling service()", t);
                throw new ServletException(t);
            }

        }
    }

    class WebSocketConnection extends Connection {
        private final WebSocketServerChannel socketChannel;

        WebSocketConnection(ParticipantId loggedInUser) {
            super(loggedInUser);
            socketChannel = new WebSocketServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        @Override
        protected void sendMessage(long sequenceNo, Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }

        public WebSocketServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }
    }

    class SocketIOConnection extends Connection {
        private final SocketIOServerChannel socketChannel;

        SocketIOConnection(ParticipantId loggedInUser) {
            super(loggedInUser);
            socketChannel = new SocketIOServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        @Override
        protected void sendMessage(long sequenceNo, Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }

        public SocketIOServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }
    }

    abstract class Connection implements ProtoCallback {
        private final Map<Long, ServerRpcController> activeRpcs = new ConcurrentHashMap<Long, ServerRpcController>();

        // The logged in user.
        // Note: Due to this bug:
        // http://code.google.com/p/wave-protocol/issues/detail?id=119,
        // the field may be null on first connect and then set later using an
        // RPC.
        private ParticipantId loggedInUser;

        /**
         * @param loggedInUser
         *            The currently logged in user, or null if no user is logged
         *            in.
         */
        public Connection(ParticipantId loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        protected void expectMessages(MessageExpectingChannel channel) {
            synchronized (registeredServices) {
                for (RegisteredServiceMethod serviceMethod : registeredServices.values()) {
                    channel.expectMessage(serviceMethod.service.getRequestPrototype(serviceMethod.method));
                    LOG.fine("Expecting: " + serviceMethod.method.getFullName());
                }
            }
            channel.expectMessage(Rpc.CancelRpc.getDefaultInstance());
        }

        protected abstract void sendMessage(long sequenceNo, Message message);

        private ParticipantId authenticate(String token) {
            HttpSession session = sessionManager.getSessionFromToken(token);
            ParticipantId user = sessionManager.getLoggedInUser(session);
            return user;
        }

        @Override
        public void message(final long sequenceNo, Message message) {
            if (message instanceof Rpc.CancelRpc) {
                final ServerRpcController controller = activeRpcs.get(sequenceNo);
                if (controller == null) {
                    throw new IllegalStateException("Trying to cancel an RPC that is not active!");
                } else {
                    LOG.info("Cancelling open RPC " + sequenceNo);
                    controller.cancel();
                }
            } else if (message instanceof ProtocolAuthenticate) {
                // Workaround for bug:
                // http://codereview.waveprotocol.org/224001/

                // When we get this message, either the connection will not be
                // logged in
                // (loggedInUser == null) or the connection will have been
                // authenticated
                // via cookies
                // (in which case loggedInUser must match the authenticated
                // user, and
                // this message has no
                // effect).

                ProtocolAuthenticate authMessage = (ProtocolAuthenticate) message;
                ParticipantId authenticatedAs = authenticate(authMessage.getToken());

                Preconditions.checkArgument(authenticatedAs != null, "Auth token invalid");
                Preconditions.checkState(loggedInUser == null || loggedInUser.equals(authenticatedAs),
                        "Session already authenticated as a different user");

                loggedInUser = authenticatedAs;
                LOG.info("Session authenticated as " + loggedInUser);
                sendMessage(sequenceNo, ProtocolAuthenticationResult.getDefaultInstance());
            } else if (registeredServices.containsKey(message.getDescriptorForType())) {
                if (activeRpcs.containsKey(sequenceNo)) {
                    throw new IllegalStateException("Can't invoke a new RPC with a sequence number already in use.");
                } else {
                    final RegisteredServiceMethod serviceMethod = registeredServices.get(message.getDescriptorForType());

                    // Create the internal ServerRpcController used to invoke
                    // the call.
                    final ServerRpcController controller = new CustomServerRpcControllerImpl(message,
                            serviceMethod.service, serviceMethod.method, loggedInUser, new RpcCallback<Message>() {
                                @Override
                                synchronized public void run(Message message) {
                                    if (message instanceof Rpc.RpcFinished
                                            || !serviceMethod.method.getOptions().getExtension(Rpc.isStreamingRpc)) {
                                        // This RPC is over - remove it from the
                                        // map.
                                        boolean failed = message instanceof Rpc.RpcFinished ? ((Rpc.RpcFinished) message).getFailed()
                                                : false;
                                        LOG.fine("RPC " + sequenceNo + " is now finished, failed = " + failed);
                                        if (failed) {
                                            LOG.info("error = " + ((Rpc.RpcFinished) message).getErrorText());
                                        }
                                        activeRpcs.remove(sequenceNo);
                                    }
                                    sendMessage(sequenceNo, message);
                                }
                            });

                    // Kick off a new thread specific to this RPC.
                    activeRpcs.put(sequenceNo, controller);
                    threadPool.execute(controller);
                }
            } else {
                // Sent a message type we understand, but don't expect -
                // erronous case!
                throw new IllegalStateException("Got expected but unknown message  (" + message + ") for sequence: "
                        + sequenceNo);
            }
        }

        @Override
        public void unknown(long sequenceNo, String messageType, UnknownFieldSet message) {
            throw new IllegalStateException("Got unknown message (type: " + messageType + ", " + message
                    + ") for sequence: " + sequenceNo);
        }

        @Override
        public void unknown(long sequenceNo, String messageType, String message) {
            throw new IllegalStateException("Got unknown message (type: " + messageType + ", " + message
                    + ") for sequence: " + sequenceNo);
        }
    }

    /**
     * Construct a new ServerRpcProvider, hosting on the specified WebSocket
     * addresses.
     * 
     * Also accepts an ExecutorService for spawning managing threads.
     */
    public CustomServerRpcProviderSamePort(InetSocketAddress[] httpAddresses, Integer flashsocketPolicyPort,
            ExecutorService threadPool, SessionManager sessionManager,
            org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this.httpAddresses = httpAddresses;
        this.flashsocketPolicyPort = flashsocketPolicyPort;
        this.threadPool = threadPool;
        this.sessionManager = sessionManager;
        this.jettySessionManager = jettySessionManager;
    }

    /**
     * Constructs a new ServerRpcProvider with a default ExecutorService.
     */
    public CustomServerRpcProviderSamePort(InetSocketAddress[] httpAddresses, Integer flashsocketPolicyPort,
            SessionManager sessionManager, org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(httpAddresses, flashsocketPolicyPort, Executors.newCachedThreadPool(), sessionManager, jettySessionManager);
    }

    @Inject
    public CustomServerRpcProviderSamePort(@Named(CoreSettings.HTTP_FRONTEND_ADDRESSES) List<String> httpAddresses,
            @Named(CoreSettings.FLASHSOCKET_POLICY_PORT) Integer flashsocketPolicyPort, SessionManager sessionManager,
            org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(parseAddressList(httpAddresses), flashsocketPolicyPort, sessionManager, jettySessionManager);
    }

    @Override
    protected void configureServlets() {
        // public void startWebSocketServer() {
        // httpServer = new Server();

        List<SelectChannelConnector> connectors = getSelectChannelConnectors(httpAddresses);
        if (connectors.isEmpty()) {
            LOG.severe("No valid http end point address provided!");
        }
        for (SelectChannelConnector connector : connectors) {
            // httpServer.addConnector(connector);
        }

        // ServletContextHandler context = new
        // ServletContextHandler(ServletContextHandler.SESSIONS);
        // if (jettySessionManager != null) {
        // context.getSessionHandler().setSessionManager(jettySessionManager);
        // }
        // context.setResourceBase("./war");

        // Servlet where the websocket connection is served from.
        // ServletHolder wsholder = new ServletHolder(new
        // WaveWebSocketServlet());
        // context.addServlet(wsholder, "/socket");
        // TODO(zamfi): fix to let messages span frames.
        // wsholder.setInitParameter("bufferSize", "" + 1024 * 1024); // 1M
        // buffer
        Map<String, String> params = new HashMap<String, String>();
        params.put("bufferSize", "" + 1024 * 1024);
        serveRegex("/socket").with(WaveWebSocketServlet.class, params);
        // Servlet where the websocket connection is served from.
        // ServletHolder sioholder = new ServletHolder(new
        // WaveSocketIOServlet());
        // context.addServlet(sioholder, "/socket.io/*");
        Map<String, String> sioParams = new HashMap<String, String>();
        // TODO(zamfi): fix to let messages span frames.
        sioParams.put("bufferSize", "" + 1024 * 1024); // 1M buffer

        // Set flash policy server parameters
        String flashPolicyServerHost = "localhost";
        StringBuilder flashPolicyAllowedPorts = new StringBuilder();
        /*
         * Loop through addresses, collect list of ports, and determine if we
         * are to use "localhost" of the AnyHost wildcard.
         */
        for (InetSocketAddress addr : httpAddresses) {
            if (flashPolicyAllowedPorts.length() > 0) {
                flashPolicyAllowedPorts.append(",");
            }
            flashPolicyAllowedPorts.append(addr.getPort());
            if (!addr.getAddress().isLoopbackAddress()) {
                // Until it's possible to pass a list of address, this is the
                // only valid alternative.
                flashPolicyServerHost = "0.0.0.0";
            }
        }
        sioParams.put(FlashSocketTransport.FLASHPOLICY_SERVER_HOST_KEY, flashPolicyServerHost);
        sioParams.put(FlashSocketTransport.FLASHPOLICY_SERVER_PORT_KEY, "" + flashsocketPolicyPort);
        // TODO: Change to use the public http address and all other bound
        // addresses.
        sioParams.put(FlashSocketTransport.FLASHPOLICY_DOMAIN_KEY, "*");
        sioParams.put(FlashSocketTransport.FLASHPOLICY_PORTS_KEY, flashPolicyAllowedPorts.toString());
        serveRegex("/socket.io/*").with(WaveSocketIOWrapperServlet.class, sioParams);

        // Serve the static content and GWT web client with the default servlet
        // (acts like a standard file-based web server).
        // ServletHolder defaultServlet = new ServletHolder(new
        // DefaultServlet());
        // context.addServlet(defaultServlet, "/static/*");
        // context.addServlet(defaultServlet, "/webclient/*");
        serveRegex("/static/*").with(DefaultServlet.class);
        serveRegex("/webclient/*").with(DefaultServlet.class);

        for (Trio<String, HttpServlet, Map<String, String>> servlet : servletNewRegistry) {
            Map<String, String> sParams = servlet.getThird();
            LOG.info("Configuring servlet for: " + servlet.getFirst());
            if (sParams == null) {
                serveRegex(servlet.getFirst()).with(servlet.getSecond().getClass());
            } else {
                serveRegex(servlet.getFirst()).with(servlet.getSecond().getClass(), sParams);
            }
            // context.addServlet(servlet.getSecond(), servlet.getFirst());
        }

        // httpServer.setHandler(context);

        try {
            // httpServer.start();
        } catch (Exception e) { // yes, .start() throws "Exception"
            LOG.severe("Fatal error starting http server.", e);
            return;
        }
        LOG.fine("Wave WebSocket server running.");
    }

    private static InetSocketAddress[] parseAddressList(List<String> addressList) {
        if (addressList == null || addressList.size() == 0) {
            return new InetSocketAddress[0];
        } else {
            Set<InetSocketAddress> addresses = Sets.newHashSet();
            for (String str : addressList) {
                if (str.length() == 0) {
                    LOG.warning("Encountered empty address in http addresses list.");
                } else {
                    try {
                        InetSocketAddress address = NetUtils.parseHttpAddress(str);
                        if (!addresses.contains(address)) {
                            addresses.add(address);
                        } else {
                            LOG.warning("Ignoring duplicate address in http addresses list: Duplicate entry '" + str
                                    + "' resolved to " + address.getAddress().getHostAddress());
                        }
                    } catch (IOException e) {
                        LOG.severe("Unable to process address " + str, e);
                    }
                }
            }
            return addresses.toArray(new InetSocketAddress[0]);
        }
    }

    /**
     * @return a list of {@link SelectChannelConnector} each bound to a
     *         host:port pair form the list addresses.
     */
    private List<SelectChannelConnector> getSelectChannelConnectors(InetSocketAddress[] httpAddresses) {
        List<SelectChannelConnector> list = Lists.newArrayList();
        for (InetSocketAddress address : httpAddresses) {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setHost(address.getAddress().getHostAddress());
            connector.setPort(address.getPort());
            list.add(connector);
        }

        return list;
    }

    public class WaveWebSocketServlet extends WebSocketServlet {
        @Override
        protected WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
            ParticipantId loggedInUser = sessionManager.getLoggedInUser(request.getSession(false));

            WebSocketConnection connection = new WebSocketConnection(loggedInUser);
            return connection.getWebSocketServerChannel();
        }
    }

    public class WaveSocketIOServlet extends SocketIOServlet {
        @Override
        protected SocketIOInbound doSocketIOConnect(HttpServletRequest request, String[] protocols) {
            ParticipantId loggedInUser = sessionManager.getLoggedInUser(request.getSession(false));

            SocketIOConnection connection = new SocketIOConnection(loggedInUser);
            return connection.getWebSocketServerChannel();
        }
    }

    /**
     * Returns the socket the WebSocket server is listening on.
     */
    public SocketAddress getWebSocketAddress() {
        // if (httpServer == null) {
        // return null;
        // } else {
        // Connector c = httpServer.getConnectors()[0];
        // return new InetSocketAddress(c.getHost(), c.getLocalPort());
        // }
        return null;
    }

    /**
     * Stops this server.
     */
    public void stopServer() throws IOException {
        try {
            // httpServer.stop(); // yes, .stop() throws "Exception"
        } catch (Exception e) {
            LOG.warning("Fatal error stopping http server.", e);
        }
        LOG.fine("server shutdown.");
    }

    /**
     * Register all methods provided by the given service type.
     */
    public void registerService(Service service) {
        synchronized (registeredServices) {
            for (MethodDescriptor methodDescriptor : service.getDescriptorForType().getMethods()) {
                registeredServices.put(methodDescriptor.getInputType(), new RegisteredServiceMethod(service,
                        methodDescriptor));
            }
        }
    }

    /**
     * Set of servlets
     */
    // List<Pair<String, ServletHolder>> servletRegistry = Lists.newArrayList();

    List<Trio<String, HttpServlet, Map<String, String>>> servletNewRegistry = Lists.newArrayList();

    /**
     * Add a servlet to the servlet registry. This servlet will be attached to
     * the specified URL pattern when the server is started up.
     * 
     * @param urlPattern
     *            URL pattern for paths. Eg, '/foo', '/foo/*'
     * @param servlet
     *            The servlet object to bind to the specified paths
     * @return the {@link ServletHolder} that holds the servlet.
     */
    public Servlet addServlet(String urlPattern, HttpServlet servlet) {
        // ServletHolder servletHolder = new ServletHolder(servlet);
        servletNewRegistry.add(new Trio<String, HttpServlet, Map<String, String>>(urlPattern, servlet, null));
        return servlet;
    }

    public Servlet addServlet(String urlPattern, HttpServlet servlet, Map<String, String> params) {
        // ServletHolder servletHolder = new ServletHolder(servlet);
        servletNewRegistry.add(new Trio<String, HttpServlet, Map<String, String>>(urlPattern, servlet, params));
        return servlet;
    }

}
