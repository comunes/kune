package org.ourproject.kune.platf.server.services;

import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SiteBarServerService implements SiteBarService {

    private final UserManager userManager;

    @Inject
    public SiteBarServerService(final UserManager userManager) {
	this.userManager = userManager;
    }

    public String login(final String nick, final String pass) throws SerializableException {
	// userManager.login();
	return "EsteSeriaElUserHash";
    }

    public void logout() throws SerializableException {
	// TODO
    }

}
