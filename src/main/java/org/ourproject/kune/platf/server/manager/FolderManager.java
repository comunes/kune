package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;

public interface FolderManager {
    Container find(Long id);

    Container createRootFolder(Group group, String toolName, String name);

    Container createFolder(Group group, Long parentFolderId, String name);
}
