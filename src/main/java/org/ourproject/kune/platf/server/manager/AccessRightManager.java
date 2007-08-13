package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessList;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.AccessRight;

public interface AccessRightManager {

    public AccessRight get(final User user, final AccessList accessList);

}
