package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.model.Content;

public interface ContentManager {
    void save(Content content, User user, String content2);

    ContentDescriptor getContent(Group group, StateToken token) throws ContentNotFoundException;

}
