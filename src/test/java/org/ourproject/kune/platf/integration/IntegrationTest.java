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

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.UserService;

import com.google.inject.Inject;

public abstract class IntegrationTest {

    @Inject
    protected UserSession session;
    @Inject
    UserService userService;
    @Inject
    DatabaseProperties properties;

    public String getHash() {
        return session.getHash();
    }

    protected String doLogin() throws DefaultException {
        return userService.login(getSiteAdminShortName(), properties.getAdminPassword()).getUserHash();
    }

    protected String doLoginWithDummyUser() throws DefaultException {
        return userService.login("dummy", properties.getAdminPassword()).getUserHash();
    }

    protected void doLogout() throws DefaultException {
        userService.logout(getHash());
    }

    protected String getDefLicense() {
        return properties.getDefaultLicense();
    }

    protected String getDefSiteGroupName() {
        return properties.getDefaultSiteShortName();
    }

    protected String getSiteAdminShortName() {
        return properties.getAdminShortName();
    }

}
