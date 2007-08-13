package org.ourproject.kune.docs.client.ui.cnt;

import org.ourproject.kune.workspace.client.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public interface DocumentContent extends WorkspaceComponent {
    void setContent(ContentDTO content);
}
