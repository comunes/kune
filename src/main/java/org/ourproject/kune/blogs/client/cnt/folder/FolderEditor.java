package org.ourproject.kune.blogs.client.cnt.folder;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerDTO;

public interface FolderEditor {
    View getView();

    void setFolder(ContainerDTO folder);
}
