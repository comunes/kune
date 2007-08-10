package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.workspace.client.Workspace;

public interface Application {

    String getDefaultToolName();

    Workspace getWorkspace();

    Dispatcher getDispatcher();

    Tool getTool(String toolName);

}
