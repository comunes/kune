package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface CreationService {
    Content saveContent(Group userGroup, Content descriptor, String content);

    Content createContent(String title, User user, Container container);

    Container createFolder(Group group, Long parentFolderId, String name);

}
