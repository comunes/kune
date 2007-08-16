package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;

interface RightsService {
    public AccessRights get(final Group userGroup, final AccessLists accessList);
}
