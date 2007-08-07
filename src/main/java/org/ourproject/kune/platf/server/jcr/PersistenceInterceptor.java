package org.ourproject.kune.platf.server.jcr;

import javax.jcr.Session;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PersistenceInterceptor implements MethodInterceptor {
    Log log = LogFactory.getLog(PersistenceInterceptor.class);

    public Object invoke(final MethodInvocation invocation) throws Throwable {

	log.debug("OPENING SESSION");
	Session session = SessionHolder.openSession();
	try {
	    Object result = invocation.proceed();
	    log.debug("SAVING SESSION");
	    session.save();
	    return result;
	} catch (Exception e) {
	    log.debug("EXCEPTION. SESSION NOT SAVED");
	    throw e;
	} finally {
	    log.debug("CLOSING SESSION");
	    SessionHolder.closeSession();
	}

    }
}
