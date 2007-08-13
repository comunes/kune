package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessList;
import org.ourproject.kune.platf.server.model.AccessRight;
import org.ourproject.kune.platf.server.model.Content;

public interface MetadataManager {

    void fill(final Content content, final AccessList accessList, final AccessRight accessRight);

}
