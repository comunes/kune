package org.ourproject.kune.blogs.client.cnt.folder.viewer;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public interface FolderViewer {
    void setFolder(ContainerDTO folder);

    View getView();
}
