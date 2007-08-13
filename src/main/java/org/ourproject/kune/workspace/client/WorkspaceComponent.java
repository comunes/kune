package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public interface WorkspaceComponent {
    View getView();

    void detach();

    void attach();

    void setContent(ContentDTO content);
}
