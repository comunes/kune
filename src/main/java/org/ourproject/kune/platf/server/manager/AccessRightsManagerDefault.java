package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRights;

public class AccessRightsManagerDefault implements AccessRightsManager {

    public AccessRights get(final User user, final AccessLists accessList) {
	Group userGroup = user.getUserGroup();
	accessList.getAdmin();
	return null;
    }

}
