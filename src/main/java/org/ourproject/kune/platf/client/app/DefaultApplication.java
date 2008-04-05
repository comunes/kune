
package org.ourproject.kune.platf.client.app;

import java.util.Iterator;
import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.extend.UIExtensionPointManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.sitebar.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class DefaultApplication implements Application {
    private final Workspace workspace;
    private final Map<String, ClientTool> tools;
    private Dispatcher dispatcher;
    private StateManager stateManager;

    public DefaultApplication(final Map<String, ClientTool> tools, final Session session,
            final UIExtensionPointManager extensionPointManager) {
        this.tools = tools;
        workspace = WorkspaceFactory.createWorkspace(session, extensionPointManager);
        workspace.attachTools(tools.values().iterator());

        DesktopView desktop = WorkspaceFactory.createDesktop(workspace, new SiteBarListener() {
            public void onUserLoggedOut() {
                dispatcher.fire(WorkspaceEvents.USER_LOGGED_OUT, null);
            }

            public void onChangeState(StateToken token) {
                stateManager.setState(token);
            }
        }, session);
        desktop.attach();

    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public ClientTool getTool(final String toolName) {
        return tools.get(toolName);
    }

    public void init(final DefaultDispatcher dispatcher, final StateManager stateManager) {
        this.dispatcher = dispatcher;
        this.stateManager = stateManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setGroupState(final String groupShortName) {
        Iterator<ClientTool> iterator = tools.values().iterator();
        while (iterator.hasNext()) {
            ClientTool tool = iterator.next();
            tool.setGroupState(groupShortName);
        }
    }

    public void start() {
        dispatcher.fireDeferred(WorkspaceEvents.START_APP, null);
    }

    public void stop() {
        dispatcher.fire(WorkspaceEvents.STOP_APP, null);
    }
}
