package org.ourproject.kune.docs.client.ui.cnt.folder.viewer;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.FolderDTO;

public interface FolderViewer {
    void setFolder(FolderDTO folder);

    View getView();
}
