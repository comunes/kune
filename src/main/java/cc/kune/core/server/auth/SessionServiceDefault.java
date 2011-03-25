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
package cc.kune.core.server.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
@Deprecated
public class SessionServiceDefault implements SessionService {

    private final Provider<HttpServletRequest> requestProvider;

    @Inject
    public SessionServiceDefault(final Provider<HttpServletRequest> requestProvider) {
        this.requestProvider = requestProvider;
    }

    @Override
    @Deprecated
    public void getNewSession() {
        // HttpSession session = getSession();
        // if (session != null) {
        // // During tests session == null
        // session.invalidate();
        // }
        // getSession(true);
        setSessionExpiration();
    }

    private HttpSession getSession() {
        return requestProvider.get().getSession();
    }

    private HttpSession getSession(final boolean create) {
        return requestProvider.get().getSession(create);
    }

    @Override
    @Deprecated
    public void setSessionExpiration() {
        // HttpSession session = getSession();
        // if (session != null) {
        // session.setMaxInactiveInterval(Session.SESSION_DURATION / 1000);
        // }
    }

}
