package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface ContentManager extends Manager<Content, Long> {

    public Content createContent(String title, User user, Container container);

    public Content save(Group userGroup, Content descriptor, String content);

}
