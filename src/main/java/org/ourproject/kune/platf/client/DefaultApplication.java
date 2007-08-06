package org.ourproject.kune.platf.client;

import java.util.HashMap;
import java.util.List;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.services.Services;
import org.ourproject.kune.platf.client.workspace.Workspace;
import org.ourproject.kune.platf.client.workspace.WorkspacePresenter;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.sitebar.client.SiteBarViewFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarPanel;
import org.ourproject.kune.sitebar.client.bar.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessagePresenter;
import org.ourproject.kune.sitebar.client.msg.SiteMessageView;

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
    private SiteBarPresenter siteBar;

    public DefaultApplication(final State state, final Services services) {
        this.state = state;
        this.tools = new HashMap();
    }

    public void init(final List toolsList) {
        final SiteMessagePresenter siteMessagePresenter = createSiteMessage();
        SiteBarPanel siteBar = createSiteBar();
        final WorkspacePanel workspace = createWorkspace(toolsList, siteMessagePresenter);
        DefaultGDesktopPane desktop = createDesktop(workspace, siteBar, siteMessagePresenter);
        initResizeListener(desktop, workspace, siteMessagePresenter);
        RootPanel.get().add(desktop);
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                int windowWidth = Window.getClientWidth();
                workspace.adjustSize(windowWidth, Window.getClientHeight());
                siteMessagePresenter.adjustWidth(windowWidth);
            }
        });
    }

    private SiteMessagePresenter createSiteMessage() {
        SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
        SiteMessageView siteMessageView = SiteBarViewFactory.createSiteMessageView(siteMessagePresenter);
        siteMessagePresenter.init(siteMessageView);
        Site.siteUserMessage = siteMessagePresenter;
        return siteMessagePresenter;
    }

    private WorkspacePanel createWorkspace(final List toolsList, final SiteMessagePresenter siteMessagePresenter) {
        prepareTools(toolsList, state);
        final WorkspacePanel view = new WorkspacePanel();
        workspace = new WorkspacePresenter(view);
        workspace.attachTools(toolsList.iterator());
        return view;
    }

    private void prepareTools(final List toolList, final State state) {
        int total = toolList.size();
        for (int index = 0; index < total; index++) {
            Tool tool = (Tool) toolList.get(index);
            tool.setState(state);
            tools.put(tool.getName(), tool);
        }
        this.defaultToolName = ((Tool) toolList.get(0)).getName();
    }

    private SiteBarPanel createSiteBar() {
        siteBar = new SiteBarPresenter();
        final SiteBarPanel siteBarView = new SiteBarPanel(siteBar);
        siteBar.init(siteBarView);
        Site.sitebar = siteBar;
        return siteBarView;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    private void initResizeListener(final DefaultGDesktopPane desktop, final WorkspacePanel workspacePanel,
            final SiteMessagePresenter siteMessage) {
        Window.addWindowResizeListener(new WindowResizeListener() {
            public void onWindowResized(final int width, final int height) {
                workspacePanel.adjustSize(width, height);
                siteMessage.adjustWidth(width);
                desktop.setWidgetPosition((Widget) siteMessage.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
            }
        });
        Window.enableScrolling(false);
    }

    private DefaultGDesktopPane createDesktop(final Widget workspacePanel, final Widget siteBar,
            final SiteMessagePresenter message) {
        DefaultGDesktopPane desktop = new DefaultGDesktopPane();
        desktop.addWidget(workspacePanel, 0, 20);
        desktop.addWidget(siteBar, 0, 0);
        desktop.addWidget((Widget) message.getView(), Window.getClientWidth() * 40 / 100 - 10, 2);
        Gwm.setOverlayLayerDisplayOnDragAction(false);
        desktop.setTheme("alphacubecustom");
        return desktop;
    }

    public Tool getTool(final String toolName) {
        return (Tool) tools.get(toolName);
    }

    public String getDefaultToolName() {
        return defaultToolName;
    }

    public void setDispatcher(final DefaultDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public State getState() {
        return state;
    }

    public SiteBar getSiteBar() {
        return siteBar;
    }

}
