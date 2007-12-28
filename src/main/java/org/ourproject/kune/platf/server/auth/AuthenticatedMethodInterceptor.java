package org.ourproject.kune.platf.server.auth;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.UserSession;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AuthenticatedMethodInterceptor implements MethodInterceptor {

    @Inject
    Provider<UserSession> userSessionProvider;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        String userHash = (String) arguments[0];

        UserSession userSession = userSessionProvider.get();

        if (!userSession.isUserLoggedIn() || userHash == null || !userSession.getHash().equals(userHash)) {
            // if (userHash == null) {
            throw new UserMustBeLoggedException();
        } else {
            Object result = invocation.proceed();
            return result;
        }
    }

}
