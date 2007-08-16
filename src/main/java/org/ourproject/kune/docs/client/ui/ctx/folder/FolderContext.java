package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;

public interface FolderContext extends ContextItems {
    void setContainer(StateToken currentState, ContainerDTO folder, AccessRightsDTO accessRightsDTO);
}
