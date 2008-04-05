
package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface DocumentContext extends WorkspaceComponent {
    void setContext(StateDTO state);

    void showAdmin();

    void showFolders();
}
