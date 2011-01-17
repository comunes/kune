package org.ourproject.kune.platf.server.manager;

import java.util.List;

import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Tag;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.User;

public interface TagUserContentManager extends Manager<TagUserContent, Long> {

    List<Tag> find(User user, Content content);

    TagCloudResult getTagCloudResultByGroup(Group group);

    String getTagsAsString(User user, Content content);

    void remove(User user, Content content);

    void setTags(User user, Content content, String tags);

}
