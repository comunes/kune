/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.rack;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.ProxyServlet;
import org.ourproject.kune.app.server.wave.CustomAttachmentServlet;
import org.ourproject.kune.app.server.wave.CustomFetchServlet;
import org.ourproject.kune.app.server.wave.CustomServerModule;
import org.ourproject.kune.app.server.wave.CustomSettingsBinder;
import org.ourproject.kune.app.server.wave.CustomUserRegistrationServlet;
import org.ourproject.kune.platf.server.ServerException;
import org.ourproject.kune.rack.dock.Dock;
import org.ourproject.kune.rack.dock.RequestMatcher;
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
import org.waveprotocol.box.server.rpc.ServerRpcProvider;
import org.waveprotocol.box.server.rpc.SignOutServlet;
import org.waveprotocol.box.server.rpc.WaveClientServlet;
import org.waveprotocol.box.server.waveserver.WaveBus;
import org.waveprotocol.wave.crypto.CertPathStore;
import org.waveprotocol.wave.federation.FederationSettings;
import org.waveprotocol.wave.federation.FederationTransport;
import org.waveprotocol.wave.federation.noop.NoOpFederationModule;
import org.waveprotocol.wave.federation.xmpp.XmppFederationModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class RackServletFilter implements Filter {

    /**
     * This is the name of the system property used to find the server config
     * file.
     */
    private static final String PROPERTY_FILE = "wave-server.properties";

    public static class DockChain implements FilterChain {
        private final Iterator<Dock> iterator;

        public DockChain(final Iterator<Dock> iterator) {
            this.iterator = iterator;
        }

        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
                ServletException {
            Dock dock = null;
            boolean matched = false;

            final String relative = RackHelper.getRelativeURL(request);
            while (!matched && iterator.hasNext()) {
                dock = iterator.next();
                matched = dock.matches(relative);
            }
            if (matched) {
                execute(dock.getFilter(), request, response);
            }
        }

        private void execute(final Filter filter, final ServletRequest request, final ServletResponse response)
                throws IOException, ServletException {
            // log.debug("RACK FILTER: " + filter.getClass().getSimpleName());
            filter.doFilter(request, response, this);
        }
    }
    public static final String INJECTOR_ATTRIBUTE = Injector.class.getName();
    private static final String MODULE_PARAMETER = RackModule.class.getName();
    private static final Log LOG = LogFactory.getLog(RackServletFilter.class);
    private List<Dock> docks;

    private List<RequestMatcher> excludes;

    @Override
    public void destroy() {
        for (final Dock dock : docks) {
            dock.getFilter().destroy();
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final String relative = RackHelper.getRelativeURL(request);
        // log.debug("REQUEST: " + relative);
        for (final RequestMatcher matcher : excludes) {
            if (matcher.matches(relative)) {
                // log.debug("SKIPING!");
                chain.doFilter(request, response);
                return;
            }
        }

        final FilterChain newChain = new DockChain(docks.iterator());
        newChain.doFilter(request, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        try {
            LOG.debug("INITIALIZING RackServletFilter...");
            final RackModule module = getModule(filterConfig);
            final RackBuilder builder = new RackBuilder();
            module.configure(builder);

            final Rack rack = builder.getRack();
            Module waveCoreSettings = CustomSettingsBinder.bindSettings(PROPERTY_FILE, CoreSettings.class);
            rack.add(waveCoreSettings);
            final Injector injector = installInjector(filterConfig, rack);
            startContainerListeners(rack.getListeners(), injector);
            docks = rack.getDocks();
            excludes = rack.getExcludes();
            initFilters(filterConfig);

            // This is a extract from Wave ServerMain.java don't integrate it
            // more
            // in our code to check for differences easily between this and the
            // WIAB
            // code
            try {
                runWaveServer(injector);
                return;
            } catch (IOException e) {
                LOG.fatal("IOException when running server:", e);
            } catch (PersistenceException e) {
                LOG.fatal("PersistenceException when running server:", e);
            }
        } catch (ConfigurationException e) {
            LOG.fatal("ConfigurationException when running server:", e);
        }
        LOG.debug("INITIALIZATION DONE!");
    }

    private RackModule getModule(final FilterConfig filterConfig) {
        final String moduleName = getModuleName(filterConfig);
        try {
            final Class<?> clazz = Class.forName(moduleName);
            final RackModule module = (RackModule) clazz.newInstance();
            return module;
        } catch (final Exception e) {
            throw new ServerException("couldn't instantiate the rack module", e);
        }
    }

    private String getModuleName(final FilterConfig filterConfig) {
        final String moduleName = filterConfig.getInitParameter(MODULE_PARAMETER);
        if (moduleName == null) {
            throw new ServerException("Rack module name can't be null!");
        }
        return moduleName;
    }

    private void initFilters(final FilterConfig filterConfig) throws ServletException {
        for (final Dock dock : docks) {
            dock.getFilter().init(filterConfig);
        }
    }

    private Injector installInjector(final FilterConfig filterConfig, final Rack rack) {
        final Injector injector = Guice.createInjector(rack.getGuiceModules());
        filterConfig.getServletContext().setAttribute(INJECTOR_ATTRIBUTE, injector);
        return injector;
    }

    private void startContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
            final Injector injector) {
        LOG.debug("STARTING CONTAINER LISTENERS...");
        for (final Class<? extends ContainerListener> listenerClass : listenerClasses) {
            final ContainerListener listener = injector.getInstance(listenerClass);
            listener.start();
        }
    }

    // FIXME: Dani, never used this:
    @SuppressWarnings("unused")
    private void stopContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
            final Injector injector) {
        LOG.debug("STOPING CONTAINER LISTENERS...");
        for (final Class<? extends ContainerListener> listenerClass : listenerClasses) {
            final ContainerListener listener = injector.getInstance(listenerClass);
            listener.stop();
        }
    }

    public static void runWaveServer(Injector settingsInjector) throws IOException, PersistenceException,
            ConfigurationException {
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

        ServerRpcProvider server = injector.getInstance(ServerRpcProvider.class);

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
        ProxyServlet.Transparent proxyServlet = new ProxyServlet.Transparent("/gadgets", "http", gadgetServerHostname,
                injector.getInstance(Key.get(int.class, Names.named(CoreSettings.GADGET_SERVER_PORT))), "/gadgets");
        ServletHolder proxyServletHolder = server.addServlet("/gadgets/*", proxyServlet);
        proxyServletHolder.setInitParameter("HostHeader", gadgetServerHostname);

        server.addServlet("/", injector.getInstance(WaveClientServlet.class));

        RobotsGateway robotsGateway = injector.getInstance(RobotsGateway.class);
        WaveBus waveBus = injector.getInstance(WaveBus.class);
        waveBus.subscribe(robotsGateway);

        ProtocolWaveClientRpc.Interface rpcImpl = injector.getInstance(ProtocolWaveClientRpc.Interface.class);
        server.registerService(ProtocolWaveClientRpc.newReflectiveService(rpcImpl));

        FederationTransport federationManager = injector.getInstance(FederationTransport.class);
        federationManager.startFederation();

        LOG.info("Starting server");
        server.startWebSocketServer();
    }
}
