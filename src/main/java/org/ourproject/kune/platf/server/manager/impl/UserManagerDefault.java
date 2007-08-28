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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserManagerDefault implements UserManager {
    private final User finder;

    @Inject
    public UserManagerDefault(final User finder) {
	this.finder = finder;
    }

    public List<User> getAll() {
	return finder.getAll();
    }

    public User getByShortName(final String shortName) {
	return finder.getByShortName(shortName);
    }

    public User login(final String nickOrEmail, final String passwd) {
	// TODO: integrate a existing Auth manager
	User user;
	try {
	    user = finder.getByShortName(nickOrEmail);
	} catch (NoResultException e) {
	    try {
		user = finder.getByEmail(nickOrEmail);
	    } catch (NoResultException e2) {
		return null;
	    }
	}
	if (user.getPassword().equals(passwd)) {
	    return user;
	} else {
	    return null;
	}
    }

    public User createUser(final String shortName, final String longName, final String email, final String passwd) {
	User user = new User(shortName, longName, email, passwd);
	return user;
    }

}
