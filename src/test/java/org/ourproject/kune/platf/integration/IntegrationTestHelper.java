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
package org.ourproject.kune.platf.integration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.server.KunePersistenceService;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.TestConstants;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.wiki.server.WikiServerModule;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.persistence.PersistenceModule;

import cc.kune.wave.server.CustomSettingsBinder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class IntegrationTestHelper {

    public static Injector createInjector() {
        Injector injector;
        try {
            injector = Guice.createInjector(CustomSettingsBinder.bindSettings(TestConstants.WAVE_TEST_PROPFILE,
                    CoreSettings.class));
            final PersistenceModule persistenceModule = injector.getInstance(PersistenceModule.class);

            final Injector childInjector = injector.createChildInjector(persistenceModule, new PlatformServerModule(),
                    new DocumentServerModule(), new ChatServerModule(), new WikiServerModule(), new AbstractModule() {
                        @Override
                        protected void configure() {
                            bindScope(SessionScoped.class, Scopes.SINGLETON);
                            bindScope(RequestScoped.class, Scopes.SINGLETON);
                            // test: use
                            // memory
                            // test_db: use
                            // mysql
                            bindConstant().annotatedWith(JpaUnit.class).to(TestConstants.PERSISTENCE_UNIT);
                            bindConstant().annotatedWith(PropertiesFileName.class).to("kune.properties");
                            bind(HttpServletRequest.class).to(HttpServletRequestMocked.class);
                        }
                    });
            return childInjector;
        } catch (final ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IntegrationTestHelper(final Object... tests) {
        final Injector injector = createInjector();
        injector.getInstance(KunePersistenceService.class).start();
        for (final Object test : tests) {
            injector.injectMembers(test);
        }
    }
}
