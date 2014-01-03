/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.core.server.DummyUserSessionManager;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegrationTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class IntegrationTest {

  /** The properties. */
  @Inject
  KuneBasicProperties properties;

  /** The session. */
  @Inject
  protected DummyUserSessionManager session;

  /** The token. */
  protected String token;

  /** The user service. */
  @Inject
  UserService userService;

  /**
   * Do login.
   * 
   * @return the user info dto
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected UserInfoDTO doLogin() throws DefaultException, IOException {
    return doLogin(getSiteAdminShortName(), properties.getAdminPassword());
  }

  /**
   * Do login.
   * 
   * @param nick
   *          the nick
   * @param pass
   *          the pass
   * @return the user info dto
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected UserInfoDTO doLogin(final String nick, final String pass) throws IOException {
    waveLogin();
    return userService.login(nick, pass, token);
  }

  /**
   * Do login with dummy user.
   * 
   * @return the string
   * @throws DefaultException
   *           the default exception
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  protected String doLoginWithDummyUser() throws DefaultException, IOException {
    waveLogin();
    return userService.login("dummy", properties.getAdminPassword(), token).getUserHash();
  }

  /**
   * Do logout.
   * 
   * @throws DefaultException
   *           the default exception
   */
  @After
  public void doLogout() throws DefaultException {
    if (session != null && session.isUserLoggedIn()) {
      session.logout();
    }
  }

  /**
   * Gets the def license.
   * 
   * @return the def license
   */
  protected String getDefLicense() {
    return properties.getDefaultLicense();
  }

  /**
   * Gets the def site long name.
   * 
   * @return the def site long name
   */
  protected String getDefSiteLongName() {
    return properties.getDefaultSiteName();
  }

  /**
   * Gets the def site short name.
   * 
   * @return the def site short name
   */
  protected String getDefSiteShortName() {
    return properties.getDefaultSiteShortName();
  }

  /**
   * Gets the hash.
   * 
   * @return the hash
   */
  public String getHash() {
    return session.getHash();
  }

  /**
   * Gets the site admin short name.
   * 
   * @return the site admin short name
   */
  protected String getSiteAdminShortName() {
    return properties.getAdminShortName();
  }

  /**
   * Wave login.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
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
