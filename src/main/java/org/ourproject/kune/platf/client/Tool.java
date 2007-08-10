package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.workspace.client.WorkspaceComponent;

public interface Tool {
    String getName();

    String getCaption();

    // String getEncodedState();

    // void setEncodedState(Object value);

    Action getStateAction();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

    void setEnvironment(Dispatcher dispatcher, State state);

    String getContextRef();

    String getContentRef();

    void setState(String group, String folder, String document);

}
