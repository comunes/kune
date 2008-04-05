
package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.User;

interface AccessRightsService {

    public AccessRights get(User user, AccessLists lists);

}
