package org.ourproject.kune.sitebar.server.rpc;

import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
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

    public void createNewGroup(final GroupDTO group) throws SerializableException {
	// TODO

    }

    public List getAllLicenses() throws SerializableException {
	// TODO Auto-generated method stub
	return null;
    }

}
