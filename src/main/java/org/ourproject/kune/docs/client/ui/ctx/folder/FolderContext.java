package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface FolderContext {
    View getView();

    void setFolder(StateToken currentState, FolderDTO folder, AccessRightsDTO accessRightsDTO);
}
