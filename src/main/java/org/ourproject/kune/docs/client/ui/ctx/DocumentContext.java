package org.ourproject.kune.docs.client.ui.ctx;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

public interface DocumentContext extends WorkspaceComponent {
    void setContent(ContentDTO content);
}
