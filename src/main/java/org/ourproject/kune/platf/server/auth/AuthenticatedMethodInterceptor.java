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
