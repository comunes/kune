package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;

public interface MetadataManager {

    void fill(final Content content, final AccessLists accessList, final AccessRights accessRight);

}
