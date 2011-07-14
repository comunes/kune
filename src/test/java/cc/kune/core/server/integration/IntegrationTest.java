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
package cc.kune.core.server.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;


import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.properties.KuneBasicProperties;

import com.google.inject.Inject;

public abstract class IntegrationTest {

    @Inject
    KuneBasicProperties properties;
    @Inject
    protected UserSession session;
    protected String token;
    @Inject
    UserService userService;

    protected String doLogin() throws DefaultException, IOException {
        waveLogin();
        return userService.login(getSiteAdminShortName(), properties.getAdminPassword(), token).getUserHash();
    }

    protected String doLoginWithDummyUser() throws DefaultException, IOException {
        waveLogin();
        return userService.login("dummy", properties.getAdminPassword(), token).getUserHash();
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

    public String getHash() {
        return session.getHash();
    }

    protected String getSiteAdminShortName() {
        return properties.getAdminShortName();
    }

    protected void waveLogin() throws IOException {
        token = "somesampletoken";
        // final ClientAuthenticator authenticator = new
        // ClientAuthenticator("http://localhost:8080"
        // + SessionManager.SIGN_IN_URL);
        // final HttpCookie cookie =
        // authenticator.authenticate("admin@localhost",
        // "easyeasy".toCharArray());
        // token = cookie.getValue();
        assertNotNull(token);
    }

}
