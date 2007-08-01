package org.ourproject.kune.platf.client;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
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

public class App {
    private WorkspacePresenter workspace;

    public App() {
    }

    public void init(KunePlatform platform) {
	final WorkspacePanel view = createWorkspace(platform);
	SiteBarPanel siteBar = createSiteBar();
	RootPanel.get().add(createDesktop(view, siteBar));
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		view.adjustSize(Window.getClientWidth(), Window.getClientHeight());
	    }
	});

    }

    private WorkspacePanel createWorkspace(KunePlatform platform) {
	final WorkspacePanel view = new WorkspacePanel();
	workspace = new WorkspacePresenter(view);
	int total = platform.getToolCount();
	for (int index = 0; index < total; index++) {
	    view.addTab(platform.getTool(index).name);
	}
	initResizeListener(view);
	return view;
    }

    private SiteBarPanel createSiteBar() {
	final SiteBarPresenter siteBarPresenter = new SiteBarPresenter();
	final SiteBarPanel siteBar = new SiteBarPanel(siteBarPresenter);
	siteBarPresenter.init(siteBar);
	return siteBar;
    }

    public WorkspacePresenter getWorkspace() {
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
}
