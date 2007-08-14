package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SiteBarServerService implements SiteBarService {

    private final UserManager userManager;
    private final UserSession session;

    @Inject
    public SiteBarServerService(final UserSession session, final UserManager userManager) {
	this.session = session;
	this.userManager = userManager;
    }

    public void login(final String nick, final String pass) throws SerializableException {
	User user = userManager.login(nick, pass);
	if (user != null) {
	    session.setUser(user);
	} else {
	    throw new UserAuthException();
	}
    }

    public void logout() throws SerializableException {
	// TODO: clear cookie
	session.setUser(null);
    }

}
