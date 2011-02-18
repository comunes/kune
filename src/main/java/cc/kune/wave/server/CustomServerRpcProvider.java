package cc.kune.wave.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.jruby.rack.RackFilter;
import org.jruby.rack.RackServletContextListener;
import org.ourproject.kune.rack.RackServletFilter;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticate;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolAuthenticationResult;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.rpc.BaseUrlHelper;
import org.waveprotocol.box.server.rpc.MessageExpectingChannel;
import org.waveprotocol.box.server.rpc.ProtoCallback;
import org.waveprotocol.box.server.rpc.Rpc;
import org.waveprotocol.box.server.rpc.ServerRpcController;
import org.waveprotocol.box.server.rpc.SocketIOServerChannel;
import org.waveprotocol.box.server.rpc.WebSocketServerChannel;
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
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.google.protobuf.UnknownFieldSet;

import de.spieleck.servlets.ProxyServlet;

/**
 * ServerRpcProvider can provide instances of type Service over an incoming
 * network socket and service incoming RPCs to these services and their methods.
 * 
 * 
 */
public class CustomServerRpcProvider {
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
        public Connection(final ParticipantId loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        private ParticipantId authenticate(final String token) {
            final HttpSession session = sessionManager.getSessionFromToken(token);
            final ParticipantId user = sessionManager.getLoggedInUser(session);
            return user;
        }

        protected void expectMessages(final MessageExpectingChannel channel) {
            synchronized (registeredServices) {
                for (final RegisteredServiceMethod serviceMethod : registeredServices.values()) {
                    channel.expectMessage(serviceMethod.service.getRequestPrototype(serviceMethod.method));
                    LOG.fine("Expecting: " + serviceMethod.method.getFullName());
                }
            }
            channel.expectMessage(Rpc.CancelRpc.getDefaultInstance());
        }

        @Override
        public void message(final long sequenceNo, final Message message) {
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
            } else if (registeredServices.containsKey(message.getDescriptorForType())) {
                if (activeRpcs.containsKey(sequenceNo)) {
                    throw new IllegalStateException("Can't invoke a new RPC with a sequence number already in use.");
                } else {
                    final RegisteredServiceMethod serviceMethod = registeredServices.get(message.getDescriptorForType());

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
                    threadPool.execute(controller);
                }
            } else {
                // Sent a message type we understand, but don't expect -
                // erronous case!
                throw new IllegalStateException("Got expected but unknown message  (" + message + ") for sequence: "
                        + sequenceNo);
            }
        }

        protected abstract void sendMessage(long sequenceNo, Message message);

        @Override
        public void unknown(final long sequenceNo, final String messageType, final String message) {
            throw new IllegalStateException("Got unknown message (type: " + messageType + ", " + message
                    + ") for sequence: " + sequenceNo);
        }

        @Override
        public void unknown(final long sequenceNo, final String messageType, final UnknownFieldSet message) {
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
    class SocketIOConnection extends Connection {
        private final SocketIOServerChannel socketChannel;

        SocketIOConnection(final ParticipantId loggedInUser) {
            super(loggedInUser);
            socketChannel = new SocketIOServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        public SocketIOServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }

        @Override
        protected void sendMessage(final long sequenceNo, final Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }
    }
    public class WaveSocketIOServlet extends SocketIOServlet {
        @Override
        protected SocketIOInbound doSocketIOConnect(final HttpServletRequest request, final String[] protocols) {
            final ParticipantId loggedInUser = sessionManager.getLoggedInUser(request.getSession(false));

            final SocketIOConnection connection = new SocketIOConnection(loggedInUser);
            return connection.getWebSocketServerChannel();
        }
    }
    public class WaveWebSocketServlet extends WebSocketServlet {
        @Override
        protected WebSocket doWebSocketConnect(final HttpServletRequest request, final String protocol) {
            final ParticipantId loggedInUser = sessionManager.getLoggedInUser(request.getSession(false));

            final WebSocketConnection connection = new WebSocketConnection(loggedInUser);
            return connection.getWebSocketServerChannel();
        }
    }
    class WebSocketConnection extends Connection {
        private final WebSocketServerChannel socketChannel;

        WebSocketConnection(final ParticipantId loggedInUser) {
            super(loggedInUser);
            socketChannel = new WebSocketServerChannel(this);
            LOG.info("New websocket connection set up for user " + loggedInUser);
            expectMessages(socketChannel);
        }

        public WebSocketServerChannel getWebSocketServerChannel() {
            return socketChannel;
        }

        @Override
        protected void sendMessage(final long sequenceNo, final Message message) {
            socketChannel.sendMessage(sequenceNo, message);
        }
    }
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
    private final String baseUrl;

    private final Integer flashsocketPolicyPort;

    private final InetSocketAddress[] httpAddresses;

    private Server httpServer = null;

    private final Set<Connection> incomingConnections = Sets.newHashSet();

    private final org.eclipse.jetty.server.SessionManager jettySessionManager;

    // Mapping from incoming protocol buffer type -> specific handler.
    private final Map<Descriptors.Descriptor, RegisteredServiceMethod> registeredServices = Maps.newHashMap();

    private final String resourceBase;

    /**
     * Set of servlets
     */
    List<Pair<String, ServletHolder>> servletRegistry = Lists.newArrayList();

    private final SessionManager sessionManager;

    private final ExecutorService threadPool;

    /**
     * Construct a new ServerRpcProvider, hosting on the specified WebSocket
     * addresses.
     * 
     * Also accepts an ExecutorService for spawning managing threads.
     */
    public CustomServerRpcProvider(final InetSocketAddress[] httpAddresses, final Integer flashsocketPolicyPort,
            final String baseUrl, final String resourceBase, final ExecutorService threadPool,
            final SessionManager sessionManager, final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this.httpAddresses = httpAddresses;
        this.flashsocketPolicyPort = flashsocketPolicyPort;
        this.baseUrl = BaseUrlHelper.removeLastSlash(baseUrl);
        this.resourceBase = resourceBase;
        this.threadPool = threadPool;
        this.sessionManager = sessionManager;
        this.jettySessionManager = jettySessionManager;
    }

    /**
     * Constructs a new ServerRpcProvider with a default ExecutorService.
     */
    public CustomServerRpcProvider(final InetSocketAddress[] httpAddresses, final Integer flashsocketPolicyPort,
            final String baseUrl, final String resourceBase, final SessionManager sessionManager,
            final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(httpAddresses, flashsocketPolicyPort, baseUrl, resourceBase, Executors.newCachedThreadPool(),
                sessionManager, jettySessionManager);
    }

    @Inject
    public CustomServerRpcProvider(@Named(CoreSettings.HTTP_FRONTEND_ADDRESSES) final List<String> httpAddresses,
            @Named(CoreSettings.FLASHSOCKET_POLICY_PORT) final Integer flashsocketPolicyPort,
            @Named(CoreSettings.HTTP_BASE_URL) final String baseUrl,
            @Named(CoreSettings.RESOURCE_BASE) final String resourceBase, final SessionManager sessionManager,
            final org.eclipse.jetty.server.SessionManager jettySessionManager) {
        this(parseAddressList(httpAddresses), flashsocketPolicyPort, baseUrl, resourceBase, sessionManager,
                jettySessionManager);
    }

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
    public ServletHolder addServlet(final String urlPattern, final Servlet servlet) {
        final ServletHolder servletHolder = new ServletHolder(servlet);
        servletRegistry.add(new Pair<String, ServletHolder>(urlPattern, servletHolder));
        return servletHolder;
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

        // FIXME This was with previous ServletContextHandler: needed?
        // final ServletContextHandler context = new
        // ServletContextHandler(ServletContextHandler.SESSIONS);

        if (jettySessionManager != null) {
            context.getSessionHandler().setSessionManager(jettySessionManager);
        }
        context.setResourceBase(resourceBase);
        context.setContextPath(baseUrl);
        context.setWar(baseUrl);
        context.setParentLoaderPriority(true);
        context.setDescriptor("WEB-INF/web.xml");

        final ServletHolder httpbindHolder = new ServletHolder(ProxyServlet.class);
        httpbindHolder.setInitParameter("remotePath", "/http-bind/");
        httpbindHolder.setInitParameter("remotePort", "5280");
        context.addServlet(httpbindHolder, "/http-bind/");

        context.addEventListener(new ServletContextListener() {

            @Override
            public void contextDestroyed(final ServletContextEvent sce) {
            }

            @Override
            public void contextInitialized(final ServletContextEvent sce) {
                final ServletContext context = sce.getServletContext();
                context.setAttribute("jruby.standalone", "true");
                context.setAttribute("rails.root", "/WEB-INF/publicspace/");
                context.setAttribute("files.prefix", "/WEB-INF/publicspace/public");
                context.setAttribute("rails.env", "production");
                context.setAttribute("jruby.max.runtimes", "1");
                context.setAttribute("public.root", "/public/");
            }
        });

        context.addEventListener(new RackServletContextListener());
        // final FilterHolder rubyRack = new
        // FilterHolder(org.jruby.rack.rails.RailsServletContextListener.class);
        // rubyRack.setFilter(new RackFilter());

        // context.getInitParameter("rails.root");

        context.addFilter(RackFilter.class, "/public/*", 0);

        context.addEventListener(new GuiceServletContextListener() {
            @Override
            protected Injector getInjector() {
                return injector;
            }
        });
        context.setAttribute(RackServletFilter.INJECTOR_PARENT_ATTRIBUTE, injector);
        final FilterHolder filterHolder = new FilterHolder(RackServletFilter.class);
        filterHolder.setInitParameter("org.ourproject.kune.rack.RackModule",
                "org.ourproject.kune.app.server.KuneRackModule");
        context.addFilter(filterHolder, "/ws/*", 0);

        // context.addFilter(GuiceFilter.class, "/*", 0);

        // Servlet where the websocket connection is served from.
        final ServletHolder wsholder = new ServletHolder(new WaveWebSocketServlet());
        context.addServlet(wsholder, "/socket");
        // TODO(zamfi): fix to let messages span frames.
        wsholder.setInitParameter("bufferSize", "" + 1024 * 1024); // 1M buffer

        // Servlet where the websocket connection is served from.
        final ServletHolder sioholder = new ServletHolder(new WaveSocketIOServlet());
        context.addServlet(sioholder, "/socket.io/*");
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
        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, "/static/*");
        context.addServlet(defaultServlet, "/webclient/*");
        context.addServlet(defaultServlet, "/ws/*");
        context.addServlet(defaultServlet, "/images/*");
        context.addServlet(defaultServlet, "/javascripts/*");
        context.addServlet(defaultServlet, "/stylesheets/*");
        context.addServlet(defaultServlet, "/templates/*");

        for (final Pair<String, ServletHolder> servlet : servletRegistry) {
            context.addServlet(servlet.getSecond(), servlet.getFirst());
        }

        httpServer.setHandler(context);

        try {
            httpServer.start();
        } catch (final Exception e) { // yes, .start() throws "Exception"
            LOG.severe("Fatal error starting http server.", e);
            return;
        }
        LOG.fine("WebSocket server running. --------");
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
