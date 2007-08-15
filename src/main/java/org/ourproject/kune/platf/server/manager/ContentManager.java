package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public interface ContentManager {
    ContentDescriptor getContent(User user, String groupName, String toolName, String folderRef, String contentRef)
	    throws ContentNotFoundException;

    void save(Content content, User user, String content2);

}
