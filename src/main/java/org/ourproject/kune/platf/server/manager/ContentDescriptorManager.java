package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface ContentDescriptorManager extends Manager<Content, Long> {

    public Content createContent(String title, User user, Folder folder);

    public Content save(Group userGroup, Content descriptor, String content);

}
