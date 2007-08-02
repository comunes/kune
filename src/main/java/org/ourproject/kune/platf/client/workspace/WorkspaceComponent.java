package org.ourproject.kune.platf.client.workspace;

public interface WorkspaceComponent {
    Object getView();
    void detach();
    void attach();
}
