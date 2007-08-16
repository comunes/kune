package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.Workspace;

public interface Application {
    ClientTool getTool(String toolName);

    Workspace getWorkspace();

    Dispatcher getDispatcher();

    StateController getStateManager();

    /**
     * informa a todas las herramientas del grupo actual
     * 
     * @param group
     */
    void setGroupState(String groupShortName);

}
