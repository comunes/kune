package org.ourproject.kune.platf.server.jcr;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class SessionHolder {
    private static final ThreadLocal<Session> sessions = new ThreadLocal<Session>();

    public static Session openSession() {
	Session session = sessions.get();
	if (session != null) {
	    throw new RuntimeException("JCR session already opened!");
	}
	session = createSession();
	sessions.set(session);
	return session;
    }

    private static Session createSession() {
	try {
	    Session session = RepositoryHolder.getRepository().login();
	    return session;
	} catch (LoginException e) {
	    throw new RuntimeException("Error creating a new session", e);
	} catch (RepositoryException e) {
	    throw new RuntimeException("Error creating a new session", e);
	}
    }
}
