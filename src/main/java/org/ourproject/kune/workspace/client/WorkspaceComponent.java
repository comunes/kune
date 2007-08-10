package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.View;

public interface WorkspaceComponent {
    View getView();

    void detach();

    void attach();

    void setState(String group, String folder, String document);
}
