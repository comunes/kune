/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.ServerModule;
import org.waveprotocol.box.server.authentication.AccountStoreHolder;
import org.waveprotocol.box.server.persistence.AccountStore;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.persistence.PersistenceModule;
import org.waveprotocol.box.server.robots.RobotApiModule;
import org.waveprotocol.box.server.waveserver.WaveServerException;
import org.waveprotocol.box.server.waveserver.WaveServerImpl;
import org.waveprotocol.wave.federation.noop.NoOpFederationModule;

import cc.kune.core.server.init.FinderRegistry;
import cc.kune.core.server.integration.HttpServletRequestMocked;
import cc.kune.core.server.properties.PropertiesFileName;
import cc.kune.wave.server.CustomSettingsBinder;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

public abstract class TestHelper {
    public static Injector create(final Module module, final String persistenceUnit, final String propetiesFileName) {
        try {
            final Injector injector = Guice.createInjector(CustomSettingsBinder.bindSettings(
                    TestConstants.WAVE_TEST_PROPFILE, CoreSettings.class));
            final PersistenceModule wavePersistModule = injector.getInstance(PersistenceModule.class);
            final NoOpFederationModule federationModule = injector.getInstance(NoOpFederationModule.class);
            final Injector childInjector = injector.createChildInjector(wavePersistModule, new ServerModule(false),
                    new RobotApiModule(), federationModule, FinderRegistry.init(new JpaPersistModule(persistenceUnit)),
                    module, new Module() {
                        @Override
                        public void configure(final Binder binder) {
                            binder.bindScope(SessionScoped.class, Scopes.SINGLETON);
                            binder.bindScope(RequestScoped.class, Scopes.SINGLETON);
                            binder.bindConstant().annotatedWith(PropertiesFileName.class).to(propetiesFileName);
                            binder.bind(HttpServletRequest.class).to(HttpServletRequestMocked.class);
                        }
                    });
            try {
                childInjector.getInstance(WaveServerImpl.class).initialize();
            } catch (final WaveServerException e) {
                e.printStackTrace();
            }
            final AccountStore accountStore = childInjector.getInstance(AccountStore.class);
            accountStore.initializeAccountStore();
            AccountStoreHolder.resetForTesting();
            AccountStoreHolder.init(accountStore,
                    childInjector.getInstance(Key.get(String.class, Names.named(CoreSettings.WAVE_SERVER_DOMAIN))));
            return childInjector;
        } catch (final ConfigurationException e) {
            e.printStackTrace();
        } catch (final PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void inject(final Object target) {
        // test: use memory
        // test_db: use mysql
        // Also configurable ein PersistenceTest
        TestHelper.create(new PlatformServerModule(), TestConstants.PERSISTENCE_UNIT, "kune.properties").injectMembers(
                target);
    }

}
