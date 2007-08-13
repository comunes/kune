package org.ourproject.kune.docs.client.ui.cnt.folder.editor;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.FolderDTO;

public interface FolderEditor {
    View getView();

    void setFolder(FolderDTO folder);
}
