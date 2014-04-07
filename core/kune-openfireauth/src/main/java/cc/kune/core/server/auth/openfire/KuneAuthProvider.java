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
package cc.kune.core.server.auth.openfire;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.waveprotocol.box.server.CoreSettings;

import cc.kune.core.client.rpcservices.UserService;

import com.googlecode.gwtrpccommlayer.client.GwtRpcService;

public class KuneAuthProvider implements AuthProvider {

    public static final Log LOG = LogFactory.getLog(KuneAuthProvider.class);
    private static final String USER_NOT_LOGGED = "User \'%s\' authentication failed";

    public static void main(final String[] args) throws UnauthorizedException {
	if (args.length == 4) {
	    final String publicUrl = args[0];
	    final String ssl = args[1];
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
	final Properties prop = new Properties();
	try {
	    final InputStream is = this.getClass().getClassLoader()
		    .getResourceAsStream("wave-server.properties");
	    prop.load(is);
	} catch (final IOException e) {
	    LOG.error("Error openning property files", e);
	}
	// FIXME! these can be multiple address
	final String publicAddress = prop
		.getProperty(CoreSettings.HTTP_FRONTEND_PUBLIC_ADDRESS);
	final String ssl = prop.getProperty(CoreSettings.ENABLE_SSL);
	createService(publicAddress, ssl);
    }

    public KuneAuthProvider(final String publicAddress, final String ssl) {
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

    @Override
    public void authenticate(final String username, final String token,
	    final String digest) throws UnauthorizedException,
	    ConnectionException, InternalUnauthenticatedException {
	throw new UnsupportedOperationException();
    }

    private void createService(final String publicAddress, final String ssl) {
	final boolean isSSL = Boolean.parseBoolean(ssl);
	// http://googlewebtoolkit.blogspot.com.es/2010/07/gwtrpccommlayer-extending-gwt-rpc-to-do.html
	try {
	    final URL url = new URL((isSSL ? "https" : "http") + "://"
		    + publicAddress + "/ws/UserService");
	    LOG.info("Service access URL: " + url);
	    final GwtRpcService service = GwtRpcService.FACTORY.newInstance();

	    userService = service.create(url, UserService.class);
	    LOG.info("UserService created");
	} catch (final Exception e) {
	    LOG.error("Error starting auth provider", e);
	}
    }

    @Override
    public String getPassword(final String arg0) throws UserNotFoundException,
	    UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDigestSupported() {
	return false;
    }

    @Override
    public boolean isPlainSupported() {
	return true;
    }

    @Override
    public void setPassword(final String arg0, final String arg1)
	    throws UserNotFoundException, UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsPasswordRetrieval() {
	return false;
    }

}
