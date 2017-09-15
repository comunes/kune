/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.server.auth.openfire;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;

import com.googlecode.gwtrpccommlayer.client.GwtRpcService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import cc.kune.core.client.rpcservices.UserService;

public class KuneAuthProvider implements AuthProvider {

  public static final Log LOG = LogFactory.getLog(KuneAuthProvider.class);
  private static final String USER_NOT_LOGGED = "User \'%s\' authentication failed";

  public static void main(final String[] args) throws UnauthorizedException {
    if (args.length == 4) {
      final String publicUrl = args[0];
      final Boolean ssl = Boolean.parseBoolean(args[1]);
      final String user = args[2];
      final String pass = args[3];
      final KuneAuthProvider auth = new KuneAuthProvider(publicUrl, ssl);
      auth.authenticate(user, pass);
    } else {
      LOG.error("Params: <public address:port> <useSsl> <user> <pass>");
    }
  }

  private UserService userService;

  public KuneAuthProvider() {
    final InputStream is = this.getClass().getClassLoader().getResourceAsStream("wave-server.conf");
    final Reader reader = new InputStreamReader(is);
    final Config config = ConfigFactory.load().withFallback(ConfigFactory.parseReader(reader));

    // LOG.error("Error openning property files");
    // FIXME! these can be multiple address
    final List<String> httpAddresses = config.getStringList("core.http_frontend_addresses");
    if (httpAddresses.size() == 0) {
      LOG.error("Error reading public address");
    } else {
      final String publicAddress = httpAddresses.get(0);
      final Boolean ssl = config.getBoolean("security.enable_ssl");
      createService(publicAddress, ssl);
    }

  }

  public KuneAuthProvider(final String publicAddress, final Boolean ssl) {
    createService(publicAddress, ssl);
  }

  @Override
  public void authenticate(final String username, final String passwdOrToken)
      throws UnauthorizedException {
    try {
      userService.checkUserAndHash(username, passwdOrToken);
      LOG.info(String.format("User \'%s\' logged", username));
    } catch (final Exception e) {
      final String msg = String.format(USER_NOT_LOGGED, username);
      LOG.warn(msg, e);
      throw new UnauthorizedException(msg);
    }
    LOG.info(String.format("User \'%s\' logged", username));
  }

  private void createService(final String publicAddress, final Boolean isSSL) {

    // http://googlewebtoolkit.blogspot.com.es/2010/07/gwtrpccommlayer-extending-gwt-rpc-to-do.html
    try {
      final URL url = new URL((isSSL ? "https" : "http") + "://" + publicAddress + "/ws/UserService");
      LOG.info("Service access URL: " + url);
      final GwtRpcService service = GwtRpcService.FACTORY.newInstance();

      userService = service.create(url, UserService.class);
      LOG.info("UserService created");
    } catch (final Exception e) {
      LOG.error("Error starting auth provider", e);
    }
  }

  @Override
  public int getIterations(final String arg0)
      throws UnsupportedOperationException, UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getPassword(final String arg0)
      throws UserNotFoundException, UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSalt(final String arg0) throws UnsupportedOperationException, UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getServerKey(final String arg0)
      throws UnsupportedOperationException, UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getStoredKey(final String arg0)
      throws UnsupportedOperationException, UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isScramSupported() {
    return false;
  }

  @Override
  public void setPassword(final String arg0, final String arg1)
      throws UserNotFoundException, UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean supportsPasswordRetrieval() {
    throw new UnsupportedOperationException();
  }

}
