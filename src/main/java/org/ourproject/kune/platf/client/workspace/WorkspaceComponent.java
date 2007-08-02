package org.ourproject.kune.platf.client.workspace;

public interface WorkspaceComponent {
    Object getView();
    void detach();
    void attach();
    void setEncodedState(String encodedState);
    String getEncodedState();
}
