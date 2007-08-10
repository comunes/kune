package org.ourproject.kune.platf.client.app;

import java.util.Map;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.WorkspaceFactory;

import com.google.gwt.user.client.ui.RootPanel;

public class DefaultApplication implements Application {
    private final Workspace workspace;
    private final String defaultToolName;
    private final Map tools;
    private final Dispatcher dispatcher;

    public DefaultApplication(final Map tools, final String defaultToolName, final Dispatcher dispatcher) {
	this.tools = tools;
	this.defaultToolName = defaultToolName;
	this.dispatcher = dispatcher;

	workspace = WorkspaceFactory.getWorkspace();
	workspace.attachTools(tools.values().iterator());
	Desktop desktop = new Desktop(workspace);
	RootPanel.get().add(desktop);
    }

    public Dispatcher getDispatcher() {
	return dispatcher;
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    public Tool getTool(final String toolName) {
	return (Tool) tools.get(toolName);
    }

    public String getDefaultToolName() {
	return defaultToolName;
    }

}
