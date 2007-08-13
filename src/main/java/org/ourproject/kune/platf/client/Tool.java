package org.ourproject.kune.platf.client;

import org.ourproject.kune.workspace.client.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public interface Tool {
    String getLabel();

    String getCaption();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

    void setContent(ContentDTO content);

}
