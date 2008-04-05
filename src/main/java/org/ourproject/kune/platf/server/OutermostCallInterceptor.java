package org.ourproject.kune.platf.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.Injector;

/*
 * See: http://tembrel.blogspot.com/2007/09/matcher-and-methodinterceptor-for-dwr.html
 *
 */

public class OutermostCallInterceptor implements MethodInterceptor {
    /**
     * Decorates a MethodInterceptor so that only the outermost invocation using
     * that interceptor will be intercepted and nested invocations willbe
     * ignored.
     */
    public static MethodInterceptor outermostCall(final MethodInterceptor interceptor) {
        return new OutermostCallInterceptor(interceptor);
    }

    /** Ensure underlying interceptor is injected. */
    @Inject
    void injectInterceptor(final Injector injector) {
        injector.injectMembers(interceptor);
    }

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        int savedCount = (Integer) count.get();
        count.set(savedCount + 1);
        try {
            if ((Integer) count.get() > 1) {
                return invocation.proceed();
            } else {
                return interceptor.invoke(invocation);
            }
        } finally {
            count.set(savedCount);
        }
    }

    private OutermostCallInterceptor(final MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    private final MethodInterceptor interceptor;

    private final ThreadLocal count = new ThreadLocal() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
}