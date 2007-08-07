package org.ourproject.kune.platf.server.jcr;

import javax.jcr.Session;

import com.google.inject.Provider;

public class SessionProvider implements Provider<Session> {

    public Session get() {
	return SessionHolder.getSession();
    }

}
