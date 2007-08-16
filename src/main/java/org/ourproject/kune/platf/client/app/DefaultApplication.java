package org.ourproject.kune.platf.client.app;

import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.StateController;
import org.ourproject.kune.platf.client.tool.Tool;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.Workspace;
import org.ourproject.kune.workspace.client.WorkspaceFactory;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DefaultApplication implements Application {
    private final Workspace workspace;
    private final Map tools;
    private Dispatcher dispatcher;
    private StateController stateManager;

    public DefaultApplication(final Map tools) {
	this.tools = tools;
	workspace = WorkspaceFactory.getWorkspace();
	workspace.attachTools(tools.values().iterator());
	Desktop desktop = new Desktop(workspace, new SiteBarListener() {
	    public void onUserLoggedIn() {
		stateManager.reload();
	    }
	});
	VerticalPanel generalVP = new VerticalPanel();
	generalVP.add(desktop);
	generalVP.addStyleName("kunebody");
	RootPanel.get().add(generalVP);
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		int windowWidth = Window.getClientWidth();
		workspace.adjustSize(windowWidth, Window.getClientHeight());
		SiteBarFactory.getSiteMessage().adjustWidth(windowWidth);
	    }
	});

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

    public void init(final DefaultDispatcher dispatcher, final StateController stateManager) {
	this.dispatcher = dispatcher;
	this.stateManager = stateManager;
    }

    public StateController getStateManager() {
	return stateManager;
    }

}
