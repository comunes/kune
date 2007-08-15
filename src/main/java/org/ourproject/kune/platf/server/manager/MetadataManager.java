package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;

public interface MetadataManager {
    Content fill(final ContentDescriptor content, final AccessLists contentAccessList, final AccessRights accessRight,
	    AccessRights folderAccessRights);
}
