package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public interface ContentManager {
    Content getContent(User user, String groupName, String toolName, String folderRef, String contentRef)
	    throws ContentNotFoundException;
}
