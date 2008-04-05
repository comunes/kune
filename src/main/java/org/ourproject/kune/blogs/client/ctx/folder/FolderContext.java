package org.ourproject.kune.blogs.client.ctx.folder;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface FolderContext {
    View getView();

    void setContainer(StateToken currentState, ContainerDTO folder, AccessRightsDTO accessRightsDTO);
}
