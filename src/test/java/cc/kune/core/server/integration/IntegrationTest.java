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

import org.junit.After;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;

public abstract class IntegrationTest {

  @Inject
  KuneBasicProperties properties;
  @Inject
  protected UserSessionManager session;
  protected String token;
  @Inject
  UserService userService;

  protected UserInfoDTO doLogin() throws DefaultException, IOException {
    return doLogin(getSiteAdminShortName(), properties.getAdminPassword());
  }

  protected UserInfoDTO doLogin(final String nick, final String pass) throws IOException {
    waveLogin();
    return userService.login(nick, pass, token);
  }

  protected String doLoginWithDummyUser() throws DefaultException, IOException {
    waveLogin();
    return userService.login("dummy", properties.getAdminPassword(), token).getUserHash();
  }

  @After
  public void doLogout() throws DefaultException {
    if (session != null && session.isUserLoggedIn()) {
      session.logout();
    }
  }

  protected String getDefLicense() {
    return properties.getDefaultLicense();
  }

  protected String getDefSiteLongName() {
    return properties.getDefaultSiteName();
  }

  protected String getDefSiteShortName() {
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
