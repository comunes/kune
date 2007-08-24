/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SiteBarServerService implements SiteBarService {

    private final UserManager userManager;
    private final UserSession session;

    @Inject
    public SiteBarServerService(final UserSession session, final UserManager userManager) {
	this.session = session;
	this.userManager = userManager;
    }

    public void login(final String nickOrEmail, final String passwd) throws SerializableException {
	User user = userManager.login(nickOrEmail, passwd);
	if (user != null) {
	    session.setUser(user);
	} else {
	    throw new UserAuthException();
	}
    }

    public void logout() throws SerializableException {
	// TODO: clear cookie
	session.setUser(null);
    }

    public void createUser(final String shortName, final String name, final String email, final String passwd) {
	// TODO
    }
}
