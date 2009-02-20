package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.domain.TagCloudResult;
import org.ourproject.kune.platf.server.domain.TagUserContent;
import org.ourproject.kune.platf.server.domain.User;

public interface TagUserContentManager extends Manager<TagUserContent, Long> {

    List<Tag> find(User user, Content content);

    TagCloudResult getTagCloudResultByGroup(Group group);

    String getTagsAsString(User user, Content content);

    void remove(User user, Content content);

    void setTags(User user, Content content, String tags);

}
