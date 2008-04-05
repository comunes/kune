
package org.ourproject.kune.platf.client.app;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public interface Application {

    ClientTool getTool(String toolName);

    Dispatcher getDispatcher();

    StateManager getStateManager();

    Workspace getWorkspace();

    /**
     * Communicates to every tool the current group
     * 
     * @param group
     */
    void setGroupState(String groupShortName);

    void start();

    void stop();

}
