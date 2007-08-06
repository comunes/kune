package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;

public interface DocumentReader extends WorkspaceComponent {
    void load(String contextRef, ContextItemDTO selectedItem);
}
