package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface DocumentContext extends WorkspaceComponent {
    void setContent(StateDTO content);

    void showAdmin();

    void showFolders();
}
