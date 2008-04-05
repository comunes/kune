package org.ourproject.kune.blogs.client.ctx;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface BlogContext extends WorkspaceComponent {
    void setContext(StateDTO state);

    void showAdmin();

    void showFolders();
}
