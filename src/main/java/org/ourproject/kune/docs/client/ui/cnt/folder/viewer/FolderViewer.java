package org.ourproject.kune.docs.client.ui.cnt.folder.viewer;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public interface FolderViewer {
    void setFolder(ContainerDTO folder);

    View getView();
}
