// @formatter:off

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

package cc.kune.wave.server;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.jetty.servlets.ProxyServlet;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolWaveClientRpc;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.ServerModule;
import org.waveprotocol.box.server.authentication.AccountStoreHolder;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.frontend.ClientFrontend;
import org.waveprotocol.box.server.frontend.ClientFrontendImpl;
import org.waveprotocol.box.server.frontend.WaveClientRpcImpl;
import org.waveprotocol.box.server.frontend.WaveletInfo;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.persistence.PersistenceModule;
import org.waveprotocol.box.server.persistence.SignerInfoStore;
import org.waveprotocol.box.server.robots.RobotApiModule;
import org.waveprotocol.box.server.robots.RobotRegistrationServlet;
import org.waveprotocol.box.server.robots.active.ActiveApiServlet;
import org.waveprotocol.box.server.robots.dataapi.DataApiOAuthServlet;
import org.waveprotocol.box.server.robots.dataapi.DataApiServlet;
import org.waveprotocol.box.server.robots.passive.RobotsGateway;
import org.waveprotocol.box.server.rpc.AttachmentInfoServlet;
import org.waveprotocol.box.server.rpc.AttachmentServlet;
import org.waveprotocol.box.server.rpc.AuthenticationServlet;
import org.waveprotocol.box.server.rpc.FetchProfilesServlet;
import org.waveprotocol.box.server.rpc.FetchServlet;
import org.waveprotocol.box.server.rpc.LocaleServlet;
import org.waveprotocol.box.server.rpc.NotificationServlet;
import org.waveprotocol.box.server.rpc.SearchServlet;
import org.waveprotocol.box.server.rpc.ServerRpcProvider;
import org.waveprotocol.box.server.rpc.SignOutServlet;
import org.waveprotocol.box.server.rpc.UserRegistrationServlet;
import org.waveprotocol.box.server.rpc.WaveRefServlet;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewBus;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewDistpatcher;
import org.waveprotocol.box.server.waveserver.WaveBus;
import org.waveprotocol.box.server.waveserver.WaveIndexer;
import org.waveprotocol.box.server.waveserver.WaveServerException;
import org.waveprotocol.box.server.waveserver.WaveletProvider;
import org.waveprotocol.box.server.waveserver.WaveletStateException;
import org.waveprotocol.wave.crypto.CertPathStore;
import org.waveprotocol.wave.federation.FederationSettings;
import org.waveprotocol.wave.federation.FederationTransport;
import org.waveprotocol.wave.federation.noop.NoOpFederationModule;
import org.waveprotocol.wave.federation.xmpp.XmppFederationModule;
import org.waveprotocol.wave.model.version.HashedVersionFactory;
import org.waveprotocol.wave.model.wave.ParticipantIdUtil;
import org.waveprotocol.wave.util.logging.Log;
import org.waveprotocol.wave.util.settings.SettingsBinder;

import cc.kune.wave.server.search.CustomSearchModule;

import com.google.gwt.logging.server.RemoteLoggingServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

// TODO: Auto-generated Javadoc
/**
 * Wave Server entrypoint.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CustomServerMain {

  /**
   * This is the name of the system property used to find the server config file.
   */
  private static final String PROPERTIES_FILE_KEY = "wave.server.config";

  /** The Constant LOG. */
  private static final Log LOG = Log.get(CustomServerMain.class);

  /**
   * The Class GadgetProxyServlet.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @SuppressWarnings("serial")
  @Singleton
  public static class GadgetProxyServlet extends HttpServlet {

    /** The proxy servlet. */
    ProxyServlet.Transparent proxyServlet;

    /**
     * Instantiates a new gadget proxy servlet.
     *
     * @param gadgetServerHostname the gadget server hostname
     * @param gadgetServerPort the gadget server port
     */
    @Inject
    public GadgetProxyServlet(@Named(CoreSettings.GADGET_SERVER_HOSTNAME) String gadgetServerHostname,
        @Named(CoreSettings.GADGET_SERVER_PORT) int gadgetServerPort){

      LOG.info("Starting GadgetProxyServlet for " + gadgetServerHostname + ":" + gadgetServerPort);
      proxyServlet = new ProxyServlet.Transparent("/gadgets", "http", gadgetServerHostname,
          gadgetServerPort,"/gadgets");
    }

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
      proxyServlet.init(config);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
      proxyServlet.service(req, res);
    }
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String... args) {
    try {
      Module coreSettings = SettingsBinder.bindSettings(PROPERTIES_FILE_KEY, CoreSettings.class);
      run(coreSettings);
      return;
    } catch (PersistenceException e) {
      LOG.severe("PersistenceException when running server:", e);
    } catch (ConfigurationException e) {
      LOG.severe("ConfigurationException when running server:", e);
    } catch (WaveServerException e) {
      LOG.severe("WaveServerException when running server:", e);
    }
  }

  /**
   * Run.
   *
   * @param coreSettings the core settings
   * @throws PersistenceException the persistence exception
   * @throws ConfigurationException the configuration exception
   * @throws WaveServerException the wave server exception
   */
  public static void run(Module coreSettings) throws PersistenceException,
      ConfigurationException, WaveServerException {
    Injector settingsInjector = Guice.createInjector(coreSettings);
    boolean enableFederation = settingsInjector.getInstance(Key.get(Boolean.class,
        Names.named(CoreSettings.ENABLE_FEDERATION)));

    int listenerCount = settingsInjector.getInstance(Key.get(Integer.class,
        Names.named(CoreSettings.LISTENER_EXECUTOR_THREAD_COUNT)));
    int waveletLoadCount = settingsInjector.getInstance(Key.get(Integer.class,
        Names.named(CoreSettings.WAVELET_LOAD_EXECUTOR_THREAD_COUNT)));
    int deltaPersistCount = settingsInjector.getInstance(Key.get(Integer.class,
        Names.named(CoreSettings.DELTA_PERSIST_EXECUTOR_THREAD_COUNT)));
    int storageContinuationCount = settingsInjector.getInstance(Key.get(Integer.class,
        Names.named(CoreSettings.STORAGE_CONTINUATION_EXECUTOR_THREAD_COUNT)));
    int lookupCount = settingsInjector.getInstance(Key.get(Integer.class,
        Names.named(CoreSettings.LOOKUP_EXECUTOR_THREAD_COUNT)));

    if (enableFederation) {
      Module federationSettings =
          SettingsBinder.bindSettings(PROPERTIES_FILE_KEY, FederationSettings.class);
      // This MUST happen first, or bindings will fail if federation is enabled.
      settingsInjector = settingsInjector.createChildInjector(federationSettings);
    }

    Module federationModule = buildFederationModule(settingsInjector, enableFederation);
    PersistenceModule persistenceModule = settingsInjector.getInstance(PersistenceModule.class);
    Module searchModule = settingsInjector.getInstance(CustomSearchModule.class);
    Injector injector =
        settingsInjector.createChildInjector(new ServerModule(enableFederation, listenerCount,
            waveletLoadCount, deltaPersistCount, storageContinuationCount, lookupCount),
               new RobotApiModule(), federationModule, persistenceModule, searchModule);

    ServerRpcProvider server = injector.getInstance(ServerRpcProvider.class);
    WaveBus waveBus = injector.getInstance(WaveBus.class);

    String domain =
      injector.getInstance(Key.get(String.class, Names.named(CoreSettings.WAVE_SERVER_DOMAIN)));
    if (!ParticipantIdUtil.isDomainAddress(ParticipantIdUtil.makeDomainAddress(domain))) {
      throw new WaveServerException("Invalid wave domain: " + domain);
    }

    initializeServer(injector, domain);
    initializeServlets(injector, server);
    initializeRobotAgents(injector, server);
    initializeRobots(injector, waveBus);
    initializeFrontend(injector, server, waveBus);
    initializeFederation(injector);
    initializeSearch(injector, waveBus);

    LOG.info("Starting server");
    server.startWebSocketServer(injector);
    reindex(injector);
  }

  /**
   * Builds the federation module.
   *
   * @param settingsInjector the settings injector
   * @param enableFederation the enable federation
   * @return the module
   * @throws ConfigurationException the configuration exception
   */
  private static Module buildFederationModule(Injector settingsInjector, boolean enableFederation)
      throws ConfigurationException {
    Module federationModule;
    if (enableFederation) {
      federationModule = settingsInjector.getInstance(XmppFederationModule.class);
    } else {
      federationModule = settingsInjector.getInstance(NoOpFederationModule.class);
    }
    return federationModule;
  }

  /**
   * Initialize server.
   *
   * @param injector the injector
   * @param waveDomain the wave domain
   * @throws PersistenceException the persistence exception
   * @throws WaveServerException the wave server exception
   */
  private static void initializeServer(Injector injector, String waveDomain)
      throws PersistenceException, WaveServerException {
    AccountStore accountStore = injector.getInstance(AccountStore.class);
    accountStore.initializeAccountStore();
    AccountStoreHolder.init(accountStore, waveDomain);

    // Initialize the SignerInfoStore.
    CertPathStore certPathStore = injector.getInstance(CertPathStore.class);
    if (certPathStore instanceof SignerInfoStore) {
      ((SignerInfoStore)certPathStore).initializeSignerInfoStore();
    }

    // Initialize the server.
    WaveletProvider waveServer = injector.getInstance(WaveletProvider.class);
    waveServer.initialize();
  }

  /**
   * Initialize servlets.
   *
   * @param injector the injector
   * @param server the server
   */
  private static void initializeServlets(Injector injector, ServerRpcProvider server) {
    // See exclude list in {@link KuneRackModule}
    server.addServlet("/gadget/gadgetlist", injector.getInstance(CustomGadgetProviderServlet.class));
    server.addServlet("/attachment/*", injector.getInstance(AttachmentServlet.class));
    server.addServlet(AttachmentServlet.ATTACHMENT_URL + "/*", injector.getInstance(AttachmentServlet.class));
    server.addServlet(AttachmentServlet.THUMBNAIL_URL + "/*", injector.getInstance(AttachmentServlet.class));
    server.addServlet(AttachmentInfoServlet.ATTACHMENTS_INFO_URL, injector.getInstance(AttachmentInfoServlet.class));

    server.addServlet(SessionManager.SIGN_IN_URL, injector.getInstance(AuthenticationServlet.class));
    server.addServlet("/auth/signout", injector.getInstance(SignOutServlet.class));
    server.addServlet("/auth/register", injector.getInstance(UserRegistrationServlet.class));
    server.addServlet("/locale/*", injector.getInstance(LocaleServlet.class));
    server.addServlet("/fetch/*", injector.getInstance(FetchServlet.class));
    server.addServlet("/search/*", injector.getInstance(SearchServlet.class));
    server.addServlet("/notification/*", injector.getInstance(NotificationServlet.class));

    server.addServlet("/robot/dataapi", injector.getInstance(DataApiServlet.class));
    server.addServlet(DataApiOAuthServlet.DATA_API_OAUTH_PATH + "/*", injector.getInstance(DataApiOAuthServlet.class));
    server.addServlet("/robot/dataapi/rpc", injector.getInstance(DataApiServlet.class));
    server.addServlet("/robot/register/*", injector.getInstance(RobotRegistrationServlet.class));
    server.addServlet("/robot/rpc", injector.getInstance(ActiveApiServlet.class));
    server.addServlet("/webclient/remote_logging", injector.getInstance(RemoteLoggingServiceImpl.class));
    server.addServlet("/wse/remote_logging", injector.getInstance(RemoteLoggingServiceImpl.class));
    server.addServlet("/ws/remote_logging", injector.getInstance(RemoteLoggingServiceImpl.class));
    server.addServlet("/profile/*", injector.getInstance(FetchProfilesServlet.class));
    server.addServlet("/waveref/*", injector.getInstance(WaveRefServlet.class));

//    boolean enableImport = injector
//            .getInstance(Key.get(Boolean.class, Names.named(CoreSettings.ENABLE_IMPORT)));
//    if (enableImport)
//      server.addServlet("/import", injector.getInstance(CustomImportServlet.class));

    String gadgetHostName =
        injector
            .getInstance(Key.get(String.class, Names.named(CoreSettings.GADGET_SERVER_HOSTNAME)));
    int port =
        injector.getInstance(Key.get(Integer.class, Names.named(CoreSettings.GADGET_SERVER_PORT)));
    Map<String, String> initParams =
        Collections.singletonMap("HostHeader", gadgetHostName + ":" + port);
    server.addServlet("/gadgets/*", injector.getInstance(GadgetProxyServlet.class), initParams);

    //server.addServlet("/", injector.getInstance(WaveClientServlet.class));
  }

  /**
   * Initialize robots.
   *
   * @param injector the injector
   * @param waveBus the wave bus
   */
  private static void initializeRobots(Injector injector, WaveBus waveBus) {
    RobotsGateway robotsGateway = injector.getInstance(RobotsGateway.class);
    waveBus.subscribe(robotsGateway);
  }

  /**
   * Initialize robot agents.
   *
   * @param injector the injector
   * @param server the server
   */
  private static void initializeRobotAgents(Injector injector, ServerRpcProvider server) {
    //server.addServlet(PasswordRobot.ROBOT_URI + "/*", injector.getInstance(PasswordRobot.class));
    //server.addServlet(PasswordAdminRobot.ROBOT_URI + "/*", injector.getInstance(PasswordAdminRobot.class));
    //server.addServlet(WelcomeRobot.ROBOT_URI + "/*", injector.getInstance(WelcomeRobot.class));
    //server.addServlet(RegistrationRobot.ROBOT_URI + "/*", injector.getInstance(RegistrationRobot.class));
  }

  /**
   * Initialize frontend.
   *
   * @param injector the injector
   * @param server the server
   * @param waveBus the wave bus
   * @throws WaveServerException the wave server exception
   */
  private static void initializeFrontend(Injector injector, ServerRpcProvider server,
      WaveBus waveBus) throws WaveServerException {
    HashedVersionFactory hashFactory = injector.getInstance(HashedVersionFactory.class);

    WaveletProvider provider = injector.getInstance(WaveletProvider.class);
    WaveletInfo waveletInfo = WaveletInfo.create(hashFactory, provider);
    ClientFrontend frontend =
        ClientFrontendImpl.create(provider, waveBus, waveletInfo);

    ProtocolWaveClientRpc.Interface rpcImpl = WaveClientRpcImpl.create(frontend, false);
    server.registerService(ProtocolWaveClientRpc.newReflectiveService(rpcImpl));
  }

  /**
   * Initialize federation.
   *
   * @param injector the injector
   */
  private static void initializeFederation(Injector injector) {
    FederationTransport federationManager = injector.getInstance(FederationTransport.class);
    federationManager.startFederation();
  }

  /**
   * Initialize search.
   *
   * @param injector the injector
   * @param waveBus the wave bus
   * @throws WaveletStateException the wavelet state exception
   * @throws WaveServerException the wave server exception
   */
  private static void initializeSearch(Injector injector, WaveBus waveBus)
      throws WaveletStateException, WaveServerException {
    PerUserWaveViewDistpatcher waveViewDistpatcher =
        injector.getInstance(PerUserWaveViewDistpatcher.class);
    PerUserWaveViewBus.Listener listener = injector.getInstance(PerUserWaveViewBus.Listener.class);
    waveViewDistpatcher.addListener(listener);
    waveBus.subscribe(waveViewDistpatcher);
  }

  private static void reindex(Injector injector) throws WaveletStateException, WaveServerException {
    WaveIndexer waveIndexer = injector.getInstance(WaveIndexer.class);
    waveIndexer.remakeIndex();
  }
}
