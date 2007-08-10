package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessRights;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public class AccessManagerDefault implements AccessManager {

    public void check(final User user, final Content content) {
	AccessRights accessRights = content.getDescriptor().getAccessRights();
    }

}
