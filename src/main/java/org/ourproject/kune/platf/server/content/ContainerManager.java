package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;

public interface ContainerManager {
    Container find(Long id);

    Container createRootFolder(Group group, String toolName, String name, String type);

    Container createFolder(Group group, Long parentFolderId, String name);
}
