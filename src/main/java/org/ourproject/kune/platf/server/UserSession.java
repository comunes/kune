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

package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class UserSession {
    private User user;
    private String hash;

    public UserSession() {
    }

    public User getUser() {
	return user;
    }

    public User setUser(final User user) {
	this.user = user;
	return user;
    }

    public String getHash() {
	return hash;
    }

    public void setHash(final String hash) {
	this.hash = hash;
    }

    public Group getGroup() {
	return user != null ? user.getUserGroup() : null;
    }

}
