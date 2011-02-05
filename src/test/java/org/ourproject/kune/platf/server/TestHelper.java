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
package org.ourproject.kune.platf.server;

import javax.servlet.http.HttpServletRequest;

import org.ourproject.kune.platf.integration.HttpServletRequestMocked;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public abstract class TestHelper {
    public static Injector create(final Module module, final String persistenceUnit, final String propetiesFileName) {
        final Injector injector = Guice.createInjector(module, new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bindScope(SessionScoped.class, Scopes.SINGLETON);
                binder.bindScope(RequestScoped.class, Scopes.SINGLETON);
                binder.bindConstant().annotatedWith(JpaUnit.class).to(persistenceUnit);
                binder.bindConstant().annotatedWith(PropertiesFileName.class).to(propetiesFileName);
                binder.bind(HttpServletRequest.class).to(HttpServletRequestMocked.class);
            }
        });
        return injector;
    }

    public static void inject(final Object target) {
        // test: use memory
        // test_db: use mysql
        // Also configurable ein PersistenceTest
        TestHelper.create(new PlatformServerModule(), "test_db", "kune.properties").injectMembers(target);
    }

}
