package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;

public interface FolderManager {
    Folder find(Long id);

    Folder createRootFolder(Group group, String toolName, String name);

    Folder createFolder(Group group, Long parentFolderId, String name);
}
