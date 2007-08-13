package org.ourproject.kune.platf.client;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public interface Tool {
    String getName();

    String getCaption();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

    void setContent(ContentDTO content);

}
