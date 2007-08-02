package org.ourproject.kune.platf.client;

import java.util.HashMap;
import java.util.List;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.services.Services;
import org.ourproject.kune.platf.client.workspace.Workspace;
import org.ourproject.kune.platf.client.workspace.WorkspacePresenter;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;
import org.ourproject.kune.sitebar.client.ui.SiteBarPanel;
import org.ourproject.kune.sitebar.client.ui.SiteBarPresenter;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class DefaultApplication implements Application {
    private WorkspacePresenter workspace;
    private final HashMap tools;
    private String defaultToolName;
    private final State state;
    private DefaultDispatcher dispatcher;

    public DefaultApplication(State state, Services services) {
	this.state = state;
	this.tools = new HashMap();
    }

    public void initTools(List toolsList) {
	final WorkspacePanel view = createWorkspace(toolsList);
	SiteBarPanel siteBar = createSiteBar();
	RootPanel.get().add(createDesktop(view, siteBar));
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		view.adjustSize(Window.getClientWidth(), Window.getClientHeight());
	    }
	});
    }

    private WorkspacePanel createWorkspace(List toolsList) {
	indexTools(toolsList);
	final WorkspacePanel view = new WorkspacePanel();
	workspace = new WorkspacePresenter(view);
	workspace.attachTools(toolsList.iterator());
	initResizeListener(view);
	return view;
    }

    private void indexTools(List toolList) {
	int total = toolList.size();
	for (int index = 0; index < total ; index++) {
	    Tool tool = (Tool) toolList.get(index);
	    tools.put(tool.getName(), tool);
	}
	this.defaultToolName = ((Tool) toolList.get(0)).getName();
    }

    private SiteBarPanel createSiteBar() {
	final SiteBarPresenter siteBarPresenter = new SiteBarPresenter();
	final SiteBarPanel siteBar = new SiteBarPanel(siteBarPresenter);
	siteBarPresenter.init(siteBar);
	return siteBar;
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    private void initResizeListener(final WorkspacePanel workspacePanel) {
	Window.addWindowResizeListener(new WindowResizeListener() {
	    public void onWindowResized(int width, int height) {
		workspacePanel.adjustSize(width, height);
	    }
	});
	Window.enableScrolling(false);
    }

    private DefaultGDesktopPane createDesktop(Widget workspacePanel, Widget siteBar) {
	DefaultGDesktopPane desktop = new DefaultGDesktopPane();
	desktop.addWidget((Widget) workspacePanel, 0, 20);
	desktop.addWidget((Widget) siteBar, 0, 0);
	Gwm.setOverlayLayerDisplayOnDragAction(false);
	desktop.setTheme("alphacubecustom");
	return desktop;
    }

    public Tool getTool(String toolName) {
	return (Tool) tools.get(toolName);
    }

    public String getDefaultToolName() {
	return defaultToolName;
    }

    public void setDispatcher(DefaultDispatcher dispatcher) {
	this.dispatcher = dispatcher;
    }

    public DefaultDispatcher getDispatcher() {
        return dispatcher;
    }

    public State getState() {
	return state;
    }

}
