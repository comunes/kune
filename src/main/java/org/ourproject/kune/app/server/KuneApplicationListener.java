package org.ourproject.kune.app.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.app.server.servlet.ApplicationListener;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;

public class KuneApplicationListener implements ApplicationListener {
    @Inject
    UserSession userSession;

    @Inject
    Logger logger;

    public void onApplicationStart(final HttpServletRequest request, final HttpServletResponse response) {
	userSession.setUser(new User("this is the user name", "short name", "", ""));
	logger.log(Level.INFO, "setting cookie!!");
	response.addCookie(new Cookie("userHash", "from.server:25938475932847"));
    }

}
