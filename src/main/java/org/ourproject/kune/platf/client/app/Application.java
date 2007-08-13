package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.tool.Tool;
import org.ourproject.kune.workspace.client.Workspace;

public interface Application {
    Tool getTool(String toolName);

    Workspace getWorkspace();

    Dispatcher getDispatcher();

    StateController getStateManager();

}
