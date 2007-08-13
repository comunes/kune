package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessList;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public class AccessListManagerDefault implements AccessListManager {

    public void check(final User user, final Content content) {
	AccessList accessList = content.getDescriptor().getAccessLists();
    }

}
