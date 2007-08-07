package org.ourproject.kune.platf.server.jcr;

import javax.jcr.Session;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class PersistenceInterceptor implements MethodInterceptor {
    public Object invoke(final MethodInvocation invocation) throws Throwable {
	Object result = null;

	Session session = SessionHolder.openSession();
	try {
	    result = invocation.proceed();
	} catch (Exception e) {

	} finally {

	}

	session.save();
	// SessionHolder.closeSession();
	return result;
    }
}
