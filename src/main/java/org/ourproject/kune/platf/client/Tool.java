package org.ourproject.kune.platf.client;

import org.ourproject.kune.workspace.client.WorkspaceComponent;

public interface Tool {
    String getName();

    String getCaption();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

}
