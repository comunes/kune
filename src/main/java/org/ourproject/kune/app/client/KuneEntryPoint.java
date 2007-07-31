package org.ourproject.kune.app.client;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.chat.client.ChatModule;
import org.ourproject.kune.docs.client.DocumentModule;
import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.docs.client.rpc.DocumentServiceMocked;
import org.ourproject.kune.home.client.HomeModule;
import org.ourproject.kune.home.client.rpc.HomeService;
import org.ourproject.kune.home.client.rpc.HomeServiceMocked;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.actions.HistoryToken;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.platf.client.rpc.KuneServiceMocked;
import org.ourproject.kune.platf.client.utils.PrefetchUtilites;
import org.ourproject.kune.platf.client.workspace.WorkspaceActions;
import org.ourproject.kune.platf.client.workspace.WorkspacePresenter;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;
import org.ourproject.kune.sitebar.client.ui.SiteBarPanel;
import org.ourproject.kune.sitebar.client.ui.SiteBarPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class KuneEntryPoint implements EntryPoint {
    private final boolean useServer;

    public KuneEntryPoint() {
        useServer = false;
    }

    public void onModuleLoad() {
        if (!useServer) mockServer();

        KunePlatform platform = new KunePlatform();
        new HomeModule().configure(platform);
        new DocumentModule().configure(platform);
        new ChatModule().configure(platform);

        final WorkspacePanel view = new WorkspacePanel();
        final WorkspacePresenter presenter = new WorkspacePresenter(platform, view);
        WorkspaceActions wsActions = new WorkspaceActions(presenter);
        platform.dispatcher.register(WorkspaceActions.NAME, wsActions);


        final SiteBarPresenter siteBarPresenter = new SiteBarPresenter();
        final SiteBarPanel siteBar = new SiteBarPanel(siteBarPresenter);
        siteBarPresenter.init(siteBar);

        initResizeListener(view);
        RootPanel.get().add(createDesktop(view, siteBar));

        int total = platform.getToolCount();
        for (int index = 0; index < total; index++) {
            view.addTab(platform.getTool(index).name);
        }

        UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                view.adjustSize(Window.getClientWidth(), Window
                        .getClientHeight());
            }
        });

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                PrefetchUtilites.preFetchImpImages();
            }
        });

        KuneServiceAsync server = KuneService.App.getInstance();
        server.getDefaultGroup(Kune.getInstance().getUserHash(), new AsyncCallback () {
            public void onFailure(Throwable caught) {
                presenter.showError(caught);
            }

            public void onSuccess(Object result) {
                presenter.setGroup((GroupDTO) result);
                History.newItem(HistoryToken.encode("workspace", "tab", 0));
            }
        });
    }

    private void mockServer() {
        KuneService.App.setMock(new KuneServiceMocked());
        HomeService.App.setMock(new HomeServiceMocked());
        DocumentService.App.setMock(new DocumentServiceMocked());
    }


    private void initResizeListener(final WorkspacePanel workspacePanel) {
        Window.addWindowResizeListener(new WindowResizeListener () {
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
