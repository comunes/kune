package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;

public interface FolderContext {
    View getView();

    void setFolder(FolderDTO folder, AccessRightsDTO accessRightsDTO);
}
