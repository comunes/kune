package org.ourproject.kune.app.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.rack.filters.ApplicationListener;

import com.google.inject.Inject;
import com.google.inject.Provider;

class KuneApplicationListener implements ApplicationListener {
    final Provider<UserSession> userSessionProvider;

    @Inject
    public KuneApplicationListener(final Provider<UserSession> userSessionProvider) {
        this.userSessionProvider = userSessionProvider;
    }

    public void doAfter(final HttpServletRequest request, final HttpServletResponse httpServletResponse) {
    }

    public void doBefore(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
    }
}