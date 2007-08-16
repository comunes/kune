package org.ourproject.kune.docs.client.ui.cnt;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface DocumentContent extends WorkspaceComponent {
    void setContent(StateDTO content);
}
