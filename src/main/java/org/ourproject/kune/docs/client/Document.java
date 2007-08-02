package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public interface Document extends WorkspaceComponent {
    void load(String contextRef, ContextItemDTO selectedItem);
}
