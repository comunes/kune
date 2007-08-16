package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.Group;

public interface Accessor {

    Access getAccess(StateToken token, Group defaultGroup, Group loggedGroup, AccessType accessType)
	    throws ContentNotFoundException, AccessViolationException;

    Access getContentAccess(Long contentId, Group group, AccessType accessType) throws ContentNotFoundException,
	    AccessViolationException;

    Access getFolderAccess(Long folderId, Group group, AccessType accessType) throws AccessViolationException,
	    ContentNotFoundException;

}
