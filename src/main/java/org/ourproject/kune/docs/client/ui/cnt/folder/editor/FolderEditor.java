package org.ourproject.kune.docs.client.ui.cnt.folder.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public interface FolderEditor {
    View getView();

    void setFolder(ContainerDTO folder);
}
