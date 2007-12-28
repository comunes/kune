package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Tag;

public interface TagManager extends Manager<Tag, Long> {

    Tag findByTagName(String tag);

}
