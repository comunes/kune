package org.ourproject.kune.workspace.client.component;

import org.ourproject.kune.platf.client.View;

public interface WorkspaceComponent {
    View getView();

    void detach();

    void attach();

}
