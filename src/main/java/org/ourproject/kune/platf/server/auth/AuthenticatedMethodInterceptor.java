package org.ourproject.kune.platf.server.auth;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;

public class AuthenticatedMethodInterceptor implements MethodInterceptor {

    // TODO Inject: UserSession
    // http://tembrel.blogspot.com/2007/09/injecting-method-interceptors-in-guice.html

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        String userHash = (String) arguments[0];

        // if (!userSession.isUserLoggedIn() || userHash == null ||
        // userSession.getHash() != userHash) {
        if (userHash == null) {
            throw new UserMustBeLoggedException();
        } else {
            Object result = invocation.proceed();
            return result;
        }
    }

}
