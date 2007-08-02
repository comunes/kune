package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;

public interface Tool {
    void useAsUser(String userHash);

    String getName();
    String getCaption();
    String getEncodedState();
    void setEncodedState(Object value);
    Action getStateAction();
    WorkspaceComponent getContext();
    WorkspaceComponent getContent();
}
