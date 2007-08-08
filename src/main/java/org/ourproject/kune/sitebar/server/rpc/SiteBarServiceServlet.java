package org.ourproject.kune.sitebar.server.rpc;

import org.ourproject.kune.sitebar.client.rpc.SiteBarService;

import com.google.gwt.user.client.rpc.SerializableException;

public class SiteBarServiceServlet implements SiteBarService {

    public String login(final String nick, final String pass) throws SerializableException {
	// TODO
	return "EsteSeriaElUserHash";
    }

    public void logout() throws SerializableException {
	// TODO
    }

}
