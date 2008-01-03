package org.ourproject.kune.platf.server.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SessionServiceDefault implements SessionService {

    private final Provider<HttpServletRequest> requestProvider;

    @Inject
    public SessionServiceDefault(final Provider<HttpServletRequest> requestProvider) {
        this.requestProvider = requestProvider;
    }

    public void getNewSession() {
        HttpSession session = getSession();
        if (session != null) {
            // During tests session == null
            session.invalidate();
        }
        getSession(true);
        setSessionExpiration();
    }

    public void setSessionExpiration() {
        HttpSession session = getSession();
        if (session != null) {
            // During tests session == null
            session.setMaxInactiveInterval(300);
        }
    }

    private HttpSession getSession() {
        return requestProvider.get().getSession();
    }

    private HttpSession getSession(final boolean create) {
        return requestProvider.get().getSession(create);
    }

}
