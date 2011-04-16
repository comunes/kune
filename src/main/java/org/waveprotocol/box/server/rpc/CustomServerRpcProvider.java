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

package org.waveprotocol.box.server.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticate;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticationResult;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.util.NetUtils;
import org.waveprotocol.wave.model.util.Pair;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.util.logging.Log;

import com.glines.socketio.server.SocketIOInbound;
import com.glines.socketio.server.SocketIOServlet;
import com.glines.socketio.server.transport.FlashSocketTransport;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.google.protobuf.UnknownFieldSet;

/**
 * CustomServerRpcProvider can provide instances of type Service over an
 * incoming network socket and service incoming RPCs to these services and their
 * methods.
 * 
 * 
 */
public class CustomServerRpcProvider {
    static abstract class Connection implements ProtoCallback {
        private final Map<Integer, ServerRpcController> activeRpcs = new ConcurrentHashMap<Integer, ServerRpcController>();

        // The logged in user.
        // Note: Due to this bug:
        // http://code.google.com/p/wave-protocol/issues/detail?id=119,
        // the field may be null on first connect and then set later using an
        // RPC.
        private ParticipantId loggedInUser;

        private final CustomServerRpcProvider provider;

        /**
         * @param loggedInUser
         *            The currently logged in user, or null if no user is logged
         *            in.
         * @param provider
         */
        public Connection(final ParticipantId loggedInUser, final CustomServerRpcProvider provider) {
            this.loggedInUser = loggedInUser;
            this.provider = provider;
        }

        private ParticipantId authenticate(final String token) {
            final HttpSession session = provider.sessionManager.getSessionFromToken(token);
            final ParticipantId user = provider.sessionManager.getLoggedInUser(session);
            return user;
        }

        protected void expectMessages(final MessageExpectingChannel channel) {
            synchronized (provider.registeredServices) {
                for (final RegisteredServiceMethod serviceMethod : provider.registeredServices.values()) {
                    channel.expectMessage(serviceMethod.service.getRequestPrototype(serviceMethod.method));
                    LOG.fine("Expecting: " + serviceMethod.method.getFullName());
                }
            }
            channel.expectMessage(Rpc.CancelRpc.getDefaultInstance());
        }

        @Override
        public void message(final int sequenceNo, final Message message) {
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

                final ProtocolAuthenticate authMessage = (ProtocolAuthenticate) message;
                final ParticipantId authenticatedAs = authenticate(authMessage.getToken());

                Preconditions.checkArgument(authenticatedAs != null, "Auth token invalid");
                Preconditions.checkState(loggedInUser == null || loggedInUser.equals(authenticatedAs),
                        "Session already authenticated as a different user");

                loggedInUser = authenticatedAs;
                LOG.info("Session authenticated as " + loggedInUser);
                sendMessage(sequenceNo, ProtocolAuthenticationResult.getDefaultInstance());
            } else if (provider.registeredServices.containsKey(message.getDescriptorForType())) {
                if (activeRpcs.containsKey(sequenceNo)) {
                    throw new IllegalStateException("Can't invoke a new RPC with a sequence number already in use.");
                } else {
                    final RegisteredServiceMethod serviceMethod = provider.registeredServices.get(message.getDescriptorForType());

                    // Create the internal ServerRpcController used to invoke
                    // the call.
                    final ServerRpcController controller = new ServerRpcControllerImpl(message, serviceMethod.service,
                            serviceMethod.method, loggedInUser, new RpcCallback<Message>() {
                                @Override
                                synchronized public void run(final Message message) {
                                    if (message instanceof Rpc.RpcFinished
                                            || !serviceMethod.method.getOptions().getExtension(Rpc.isStreamingRpc)) {
                                        // This RPC is over - remove it from the
                                        // map.
                                        final boolean failed = message instanceof Rpc.RpcFinished ? ((Rpc.RpcFinished) message).getFailed()
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
                    provider.threadPool.execute(controller);
                }
            } else {
                // Sent a message type we understand, but don't expect -
                // erronous case!
                throw new IllegalStateException("Got expected but unknown message  (" + message + ") for sequence: "
                        + sequenceNo);
            }
        }

        protected abstract void sendMessage(int sequenceNo, Message message);

        @Override
        public void unknown(final int sequenceNo, final String messageType, final String message) {
            throw new IllegalStateException("Got unknown message (type: " + messageType + ", " + message
                    + ") for sequence: " + sequenceNo);
        }

        @Override
        public void unknown(final int sequenceNo, final String messageType, final UnknownFieldSet message) {
            throw new IllegalStateException("Got unknown message (type: " + messageType + ", " + message
                    + ") for sequence: " + sequenceNo);
        }
    }

    /**
     * Internal, static container class for any specific registered service
     * method.
     */
    static class RegisteredServiceMethod {
        final MethodDescriptor method;
        final Service service;

        RegisteredServiceMethod(final Service service, final MethodDescriptor method) {
            this.service = service;
            this.method = method;
        }
    }

    static class SocketIOConnection extends Connection {
        private final SocketIOServerChannel socketChannel;

        SocketIOConnection(final ParticipantId loggedInUser, final CustomServerRpcProvider provider) {
            super(loggedInUser, provider);
            socketChannel = new SocketIOServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        public SocketIOServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }

        @Override
        protected void sendMessage(final int sequenceNo, final Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }
    }
    @SuppressWarnings("serial")
    @Singleton
    public static class WaveSocketIOServlet extends HttpServlet {

        CustomServerRpcProvider provider;

        SocketIOServlet socketIOServlet = new SocketIOServlet() {
            @Override
            protected SocketIOInbound doSocketIOConnect(final HttpServletRequest request, final String[] strings) {
                final ParticipantId loggedInUser = provider.sessionManager.getLoggedInUser(request.getSession(false));

                final SocketIOConnection connection = new SocketIOConnection(loggedInUser, provider);
                return connection.getWebSocketServerChannel();
            }
        };

        @Inject
        public WaveSocketIOServlet(final CustomServerRpcProvider provider) {
            super();
            this.provider = provider;
        }

        @Override
        public void init(final ServletConfig config) throws ServletException {
            socketIOServlet.init(config);
        }

        @Override
        protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
                IOException {
            socketIOServlet.service(req, resp);
        }

        @Override
        public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
            socketIOServlet.service(req, res);
        }
    }
    @SuppressWarnings("serial")
    @Singleton
    public static class WaveWebSocketServlet extends WebSocketServlet {

        CustomServerRpcProvider provider;

        @Inject
        public WaveWebSocketServlet(final CustomServerRpcProvider provider) {
            super();
            this.provider = provider;
        }

        @Override
        protected WebSocket doWebSocketConnect(final HttpServletRequest request, final String protocol) {
            final ParticipantId loggedInUser = provider.sessionManager.getLoggedInUser(request.getSession(false));

            final WebSocketConnection connection = new WebSocketConnection(loggedInUser, provider);
            return connection.getWebSocketServerChannel();
        }
    }
    static class WebSocketConnection extends Connection {
        private final WebSocketServerChannel socketChannel;

        WebSocketConnection(final ParticipantId loggedInUser, final CustomServerRpcProvider provider) {
            super(loggedInUser, provider);
            socketChannel = new WebSocketServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        public WebSocketServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }

        @Override
        protected void sendMessage(final int sequenceNo, final Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }
    }
    // We can retrieve the injector from the context attributes via this
    // attribute name
    public static final String INJECTOR_ATTRIBUTE = Injector.class.getName();
    private static final Log LOG = Log.get(CustomServerRpcProvider.class);

    private static InetSocketAddress[] parseAddressList(final List<String> addressList) {
        if (addressList == null || addressList.size() == 0) {
            return new InetSocketAddress[0];
        } else {
            final Set<InetSocketAddress> addresses = Sets.newHashSet();
            for (final String str : addressList) {
                if (str.length() == 0) {
                    LOG.warning("Encountered empty address in http addresses list.");
                } else {
                    try {
                        final InetSocketAddress address = NetUtils.parseHttpAddress(str);
                        if (!addresses.contains(address)) {
                            addresses.add(address);
                        } else {
                            LOG.warning("Ignoring duplicate address in http addresses list: Duplicate entry '" + str
                                    + "' resolved to " + address.getAddress().getHostAddress());
                        }
                    } catch (final IOException e) {
                        LOG.severe("Unable to process address " + str, e);
                    }
                }
            }
            return addresses.toArray(new InetSocketAddress[0]);
        }
    }

    private final Integer flashsocketPolicyPort;

    private final InetSocketAddress[] httpAddresses;

    private Server httpServer = null;

    private final org.eclipse.jetty.server.SessionManager jettySessionManager;

    // Mapping from incoming protocol buffer type -> specific handler.
    private final Map<Descriptors.Descriptor, RegisteredServiceMethod> registeredServices = Maps.newHashMap();

    // List of webApp source directories ("./war", etc)
    private final String[] resourceBases;

    /**
     * Set of servlets
     */
    List<Pair<String, ServletHolder>> servletRegistry = Lists.newArrayList();

    private final SessionManager sessionManager;

    private final ExecutorService threadPool;

    /**
     * Construct a new CustomServerRpcProvider, hosting on the specified
     * WebSocket addresses.
     * 
     * Also accepts an ExecutorService for spawning managing threads.
     */
    public CustomServerRpcProvider(final InetSocketAddress[] httpAddresses, final Integer flashsocketPolicyPort,
            final String[] resourceBases, final ExecutorService threadPool, final SessionManager sessionManager,
            final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this.httpAddresses = httpAddresses;
        this.flashsocketPolicyPort = flashsocketPolicyPort;
        this.resourceBases = resourceBases;
        this.threadPool = threadPool;
        this.sessionManager = sessionManager;
        this.jettySessionManager = jettySessionManager;
    }

    /**
     * Constructs a new CustomServerRpcProvider with a default ExecutorService.
     */
    public CustomServerRpcProvider(final InetSocketAddress[] httpAddresses, final Integer flashsocketPolicyPort,
            final String[] resourceBases, final SessionManager sessionManager,
            final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(httpAddresses, flashsocketPolicyPort, resourceBases, Executors.newCachedThreadPool(), sessionManager,
                jettySessionManager);
    }

    @Inject
    public CustomServerRpcProvider(@Named(CoreSettings.HTTP_FRONTEND_ADDRESSES) final List<String> httpAddresses,
            @Named(CoreSettings.FLASHSOCKET_POLICY_PORT) final Integer flashsocketPolicyPort,
            @Named(CoreSettings.RESOURCE_BASES) final List<String> resourceBases, final SessionManager sessionManager,
            final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(parseAddressList(httpAddresses), flashsocketPolicyPort, resourceBases.toArray(new String[0]),
                sessionManager, jettySessionManager);
    }

    /**
     * Add a servlet to the servlet registry. This servlet will be attached to
     * the specified URL pattern when the server is started up.
     * 
     * @param urlPattern
     *            the URL pattern for paths. Eg, '/foo', '/foo/*'.
     * @param servlet
     *            the servlet class to bind to the specified paths.
     * @return the {@link ServletHolder} that holds the servlet.
     */
    public ServletHolder addServlet(final String urlPattern, final Class<? extends HttpServlet> servlet) {
        return addServlet(urlPattern, servlet, null);
    }

    /**
     * Add a servlet to the servlet registry. This servlet will be attached to
     * the specified URL pattern when the server is started up.
     * 
     * @param urlPattern
     *            the URL pattern for paths. Eg, '/foo', '/foo/*'.
     * @param servlet
     *            the servlet class to bind to the specified paths.
     * @param initParams
     *            the map with init params, can be null or empty.
     * @return the {@link ServletHolder} that holds the servlet.
     */
    public ServletHolder addServlet(final String urlPattern, final Class<? extends HttpServlet> servlet,
            @Nullable final Map<String, String> initParams) {
        final ServletHolder servletHolder = new ServletHolder(servlet);
        if (initParams != null) {
            servletHolder.setInitParameters(initParams);
        }
        servletRegistry.add(new Pair<String, ServletHolder>(urlPattern, servletHolder));
        return servletHolder;
    }

    public void addWebSocketServlets() {
        // Servlet where the websocket connection is served from.
        final ServletHolder wsholder = addServlet("/socket", WaveWebSocketServlet.class);
        // TODO(zamfi): fix to let messages span frames.
        wsholder.setInitParameter("bufferSize", "" + 1024 * 1024); // 1M buffer

        // Servlet where the websocket connection is served from.
        final ServletHolder sioholder = addServlet("/socket.io/*", WaveSocketIOServlet.class);
        // TODO(zamfi): fix to let messages span frames.
        sioholder.setInitParameter("bufferSize", "" + 1024 * 1024); // 1M buffer
        // Set flash policy server parameters
        String flashPolicyServerHost = "localhost";
        final StringBuilder flashPolicyAllowedPorts = new StringBuilder();
        /*
         * Loop through addresses, collect list of ports, and determine if we
         * are to use "localhost" of the AnyHost wildcard.
         */
        for (final InetSocketAddress addr : httpAddresses) {
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
        sioholder.setInitParameter(FlashSocketTransport.FLASHPOLICY_SERVER_HOST_KEY, flashPolicyServerHost);
        sioholder.setInitParameter(FlashSocketTransport.FLASHPOLICY_SERVER_PORT_KEY, "" + flashsocketPolicyPort);
        // TODO: Change to use the public http address and all other bound
        // addresses.
        sioholder.setInitParameter(FlashSocketTransport.FLASHPOLICY_DOMAIN_KEY, "*");
        sioholder.setInitParameter(FlashSocketTransport.FLASHPOLICY_PORTS_KEY, flashPolicyAllowedPorts.toString());

        // Serve the static content and GWT web client with the default servlet
        // (acts like a standard file-based web server).
        addServlet("/static/*", DefaultServlet.class);
        addServlet("/webclient/*", DefaultServlet.class);
    }

    /**
     * @return a list of {@link SelectChannelConnector} each bound to a
     *         host:port pair form the list addresses.
     */
    private List<SelectChannelConnector> getSelectChannelConnectors(final InetSocketAddress[] httpAddresses) {
        final List<SelectChannelConnector> list = Lists.newArrayList();
        for (final InetSocketAddress address : httpAddresses) {
            final SelectChannelConnector connector = new SelectChannelConnector();
            connector.setHost(address.getAddress().getHostAddress());
            connector.setPort(address.getPort());
            list.add(connector);
        }

        return list;
    }

    public ServletModule getServletModule(final Injector injector) {

        return new ServletModule() {
            @Override
            protected void configureServlets() {
                // We add servlets here to override the DefaultServlet automatic
                // registered by WebAppContext
                // in path "/" with our WaveClientServlet. Any other way to do
                // this?
                // Related question (unanswered 08-Apr-2011)
                // http://web.archiveorange.com/archive/v/d0LdlXf1kN0OXyPNyQZp
                for (final Pair<String, ServletHolder> servlet : servletRegistry) {
                    final String url = servlet.getFirst();
                    @SuppressWarnings({ "unchecked" })
                    final Class<HttpServlet> clazz = servlet.getSecond().getHeldClass();
                    @SuppressWarnings({ "unchecked" })
                    final Map<String, String> params = servlet.getSecond().getInitParameters();
                    serve(url).with(clazz, params);
                    bind(clazz).in(Singleton.class);
                }
            }
        };
    }

    /**
     * Returns the socket the WebSocket server is listening on.
     */
    public SocketAddress getWebSocketAddress() {
        if (httpServer == null) {
            return null;
        } else {
            final Connector c = httpServer.getConnectors()[0];
            return new InetSocketAddress(c.getHost(), c.getLocalPort());
        }
    }

    /**
     * Register all methods provided by the given service type.
     */
    public void registerService(final Service service) {
        synchronized (registeredServices) {
            for (final MethodDescriptor methodDescriptor : service.getDescriptorForType().getMethods()) {
                registeredServices.put(methodDescriptor.getInputType(), new RegisteredServiceMethod(service,
                        methodDescriptor));
            }
        }
    }

    public void startWebSocketServer(final Injector injector) {
        httpServer = new Server();

        final List<SelectChannelConnector> connectors = getSelectChannelConnectors(httpAddresses);
        if (connectors.isEmpty()) {
            LOG.severe("No valid http end point address provided!");
        }
        for (final SelectChannelConnector connector : connectors) {
            httpServer.addConnector(connector);
        }
        final WebAppContext context = new WebAppContext();

        context.setParentLoaderPriority(true);
        context.setAttribute(INJECTOR_ATTRIBUTE, injector);

        if (jettySessionManager != null) {
            context.getSessionHandler().setSessionManager(jettySessionManager);
        }
        final ResourceCollection resources = new ResourceCollection(resourceBases);
        context.setBaseResource(resources);

        addWebSocketServlets();

        try {
            final Injector parentInjector;

            // If we have a null injector at least bind the
            // CustomServerRpcProvider for nested static classes
            if (injector == null) {
                parentInjector = Guice.createInjector(new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(CustomServerRpcProvider.class).toInstance(CustomServerRpcProvider.this);
                    }
                });
            } else {
                parentInjector = injector;
            }

            // final ServletModule servletModule =
            // getServletModule(parentInjector);

            final ServletContextListener contextListener = new GuiceServletContextListener() {

                private final Injector childInjector = parentInjector; // .createChildInjector(servletModule);

                @Override
                protected Injector getInjector() {
                    return childInjector;
                }
            };

            context.addEventListener(contextListener);
            LOG.fine("Commented guice filter.");
            // context.addFilter(GuiceFilter.class, "/*", 0);
            httpServer.setHandler(context);

            httpServer.start();

        } catch (final Exception e) { // yes, .start() throws "Exception"
            LOG.severe("Fatal error starting http server.", e);
            return;
        }
        LOG.fine("WebSocket server running.");
    }

    /**
     * Stops this server.
     */
    public void stopServer() throws IOException {
        try {
            httpServer.stop(); // yes, .stop() throws "Exception"
        } catch (final Exception e) {
            LOG.warning("Fatal error stopping http server.", e);
        }
        LOG.fine("server shutdown.");
    }
}
