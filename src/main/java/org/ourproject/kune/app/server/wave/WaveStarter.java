package org.ourproject.kune.app.server.wave;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.servlets.ProxyServlet;
import org.waveprotocol.box.common.comms.WaveClientRpc.ProtocolWaveClientRpc;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.AccountStoreHolder;
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.persistence.PersistenceModule;
import org.waveprotocol.box.server.persistence.SignerInfoStore;
import org.waveprotocol.box.server.robots.RobotApiModule;
import org.waveprotocol.box.server.robots.passive.RobotsGateway;
import org.waveprotocol.box.server.rpc.AuthenticationServlet;
import org.waveprotocol.box.server.rpc.SignOutServlet;
import org.waveprotocol.box.server.rpc.WaveClientServlet;
import org.waveprotocol.box.server.waveserver.WaveBus;
import org.waveprotocol.wave.crypto.CertPathStore;
import org.waveprotocol.wave.federation.FederationSettings;
import org.waveprotocol.wave.federation.FederationTransport;
import org.waveprotocol.wave.federation.noop.NoOpFederationModule;
import org.waveprotocol.wave.federation.xmpp.XmppFederationModule;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class WaveStarter {

    private static final Log LOG = LogFactory.getLog(WaveStarter.class);
    private static final String PROPERTY_FILE = "wave-server.properties";

    public WaveStarter() {
    }

    public void run(Injector settingsInjector) {

        try {
            Module coreSettings = CustomSettingsBinder.bindSettings(PROPERTY_FILE, CoreSettings.class);
            settingsInjector = settingsInjector.createChildInjector(coreSettings);
            // Injector settingsInjector = Guice.createInjector(coreSettings);
            // settingsInjector.createChildInjector(coreSettings);

            boolean enableFederation = settingsInjector.getInstance(Key.get(Boolean.class,
                    Names.named(CoreSettings.ENABLE_FEDERATION)));
            Module federationModule;
            if (enableFederation) {
                Module federationSettings = CustomSettingsBinder.bindSettings(PROPERTY_FILE, FederationSettings.class);
                settingsInjector = settingsInjector.createChildInjector(federationSettings);
                federationModule = settingsInjector.getInstance(XmppFederationModule.class);
            } else {
                federationModule = settingsInjector.getInstance(NoOpFederationModule.class);
            }

            PersistenceModule persistenceModule = settingsInjector.getInstance(PersistenceModule.class);

            Injector injector = settingsInjector.createChildInjector(new CustomServerModule(enableFederation),
                    new RobotApiModule(), federationModule, persistenceModule);

            AccountStore accountStore = injector.getInstance(AccountStore.class);
            accountStore.initializeAccountStore();
            AccountStoreHolder.init(accountStore,
                    injector.getInstance(Key.get(String.class, Names.named(CoreSettings.WAVE_SERVER_DOMAIN))));

            // Initialize the SignerInfoStore
            CertPathStore certPathStore = injector.getInstance(CertPathStore.class);
            if (certPathStore instanceof SignerInfoStore) {
                ((SignerInfoStore) certPathStore).initializeSignerInfoStore();
            }

            CustomServerRpcProvider server = injector.getInstance(CustomServerRpcProvider.class);

            server.addServlet("/attachment/*", injector.getInstance(CustomAttachmentServlet.class));

            server.addServlet(SessionManager.SIGN_IN_URL, injector.getInstance(AuthenticationServlet.class));
            server.addServlet("/auth/signout", injector.getInstance(SignOutServlet.class));
            server.addServlet("/auth/register", injector.getInstance(CustomUserRegistrationServlet.class));

            server.addServlet("/fetch/*", injector.getInstance(CustomFetchServlet.class));

            // server.addServlet("/robot/dataapi",
            // injector.getInstance(DataApiServlet.class));
            // server.addServlet(DataApiOAuthServlet.DATA_API_OAUTH_PATH + "/*",
            // injector.getInstance(DataApiOAuthServlet.class));
            // server.addServlet("/robot/dataapi/rpc",
            // injector.getInstance(DataApiServlet.class));
            // server.addServlet("/robot/register/*",
            // injector.getInstance(RobotRegistrationServlet.class));
            // server.addServlet("/robot/rpc",
            // injector.getInstance(ActiveApiServlet.class));

            String gadgetServerHostname = injector.getInstance(Key.get(String.class,
                    Names.named(CoreSettings.GADGET_SERVER_HOSTNAME)));
            ProxyServlet.Transparent proxyServlet = new ProxyServlet.Transparent("/gadgets", "http",
                    gadgetServerHostname, injector.getInstance(Key.get(int.class,
                            Names.named(CoreSettings.GADGET_SERVER_PORT))), "/gadgets");
            // FIXME ServletHolder proxyServletHolder =
            Map<String, String> params = new HashMap<String, String>();
            // proxyServletHolder.setInitParameter("HostHeader",
            // gadgetServerHostname);
            params.put("HostHeader", gadgetServerHostname);
            // server.addServlet("/gadgets/*", proxyServlet, params);

            server.addServlet("/", injector.getInstance(WaveClientServlet.class));

            RobotsGateway robotsGateway = injector.getInstance(RobotsGateway.class);
            WaveBus waveBus = injector.getInstance(WaveBus.class);
            waveBus.subscribe(robotsGateway);

            ProtocolWaveClientRpc.Interface rpcImpl = injector.getInstance(ProtocolWaveClientRpc.Interface.class);
            server.registerService(ProtocolWaveClientRpc.newReflectiveService(rpcImpl));

            FederationTransport federationManager = injector.getInstance(FederationTransport.class);
            federationManager.startFederation();
            // server.startWebSocketServer();
            // injector.createChildInjector(new WaveStarterModule(server));
            LOG.info("Starting wave server");
            server.startWebSocketServer();
            // } catch (IOException e) {
            // LOG.error("IOException when running server:", e);
        } catch (PersistenceException e) {
            LOG.error("PersistenceException when running server:", e);
        } catch (ConfigurationException e) {
            LOG.error("ConfigurationException when running server:", e);
        }
    }

    public void runCont(Injector settingsInjector) {
    }
}
