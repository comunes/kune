package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.workspace.Workspace;

public interface Application {
    State getState();
    String getDefaultToolName();
    Workspace getWorkspace();
    Dispatcher getDispatcher();
    Tool getTool(String toolName);
}
