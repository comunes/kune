package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.domain.TagResult;

public interface TagManager extends Manager<Tag, Long> {

    Tag findByTagName(String tag);

    List<TagResult> getSummaryByGroup(Group group);

}
