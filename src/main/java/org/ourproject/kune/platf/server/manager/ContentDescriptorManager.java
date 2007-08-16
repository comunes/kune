package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

public interface ContentDescriptorManager extends Manager<ContentDescriptor, Long> {

    public ContentDescriptor createContent(String title, User user, Folder folder);

    public ContentDescriptor save(Group userGroup, ContentDescriptor descriptor, String content);

}
