package org.ourproject.kune.platf.server.jcr;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

public class SessionHolder {
    private static final ThreadLocal<Session> sessions = new ThreadLocal<Session>();

    public static Session getSession() {
	return sessions.get();
    }

    public static Session openSession() {
	Session session = sessions.get();
	if (session != null) {
	    throw new RuntimeException("JCR session already opened!");
	}
	session = createSession();
	sessions.set(session);
	return session;
    }

    public static void closeSession() {
	sessions.get().logout();
	sessions.set(null);
    }

    private static Session createSession() {
	try {
	    Credentials credentials = new SimpleCredentials("user", "password".toCharArray());
	    Session session = RepositoryHolder.getRepository().login(credentials);
	    return session;
	} catch (LoginException e) {
	    throw new RuntimeException("Error creating a new session", e);
	} catch (RepositoryException e) {
	    throw new RuntimeException("Error creating a new session", e);
	}
    }

}
