/*
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

package org.ourproject.kune.platf.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.UserSession;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthenticatedMethodInterceptor implements MethodInterceptor {

    @Inject
    Provider<UserSession> userSessionProvider;

    @Inject
    Provider<HttpServletRequest> requestProvider;

    @Inject
    Provider<SessionService> sessionServiceProvider;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        String userHash = (String) arguments[0];

        UserSession userSession = userSessionProvider.get();
        SessionService sessionService = sessionServiceProvider.get();

        Authenticated authAnnotation = invocation.getStaticPart().getAnnotation(Authenticated.class);
        boolean mandatory = authAnnotation.mandatory();

        if (userHash == null && mandatory) {
            sessionService.getNewSession();
            throw new UserMustBeLoggedException();
        } else if (userSession.isUserNotLoggedIn() && mandatory) {
            sessionService.getNewSession();
            throw new SessionExpiredException();
        } else if (userSession.isUserNotLoggedIn() && userHash == null) {
            // Ok, do nothing
        } else if (userSession.isUserNotLoggedIn() && userHash != null) {
            sessionService.getNewSession();
            throw new SessionExpiredException();
        } else if (!userSession.getHash().equals(userHash)) {
            userSession.logout();
            sessionService.getNewSession();
            throw new SessionExpiredException();
        }
        Object result = invocation.proceed();
        return result;
    }

}
