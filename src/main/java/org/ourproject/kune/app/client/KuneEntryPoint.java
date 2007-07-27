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
import org.ourproject.kune.platf.client.HistoryToken;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.platf.client.rpc.KuneServiceMocked;
import org.ourproject.kune.platf.client.workspace.WorkspaceActions;
import org.ourproject.kune.platf.client.workspace.WorkspaceDefault;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;

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
        
        final WorkspacePanel workspacePanel = new WorkspacePanel();
        final WorkspaceDefault workspace = new WorkspaceDefault(platform, workspacePanel);
        WorkspaceActions wsActions = new WorkspaceActions(workspace);
        platform.dispatcher.register(WorkspaceActions.NAME, wsActions);
        
        initResizeListener(workspacePanel);
        RootPanel.get().add(createDesktop(workspacePanel));

        int total = platform.getToolCount();
        for (int index = 0; index < total; index++) {
            workspacePanel.addTab(platform.getTool(index).name);
        }
        UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                workspacePanel.adjustSize(Window.getClientWidth(), Window
                        .getClientHeight());
            }
        });
        
        KuneServiceAsync server = KuneService.App.getInstance();
        server.getDefaultGroup(Kune.getUserHash(), new AsyncCallback () {
            public void onFailure(Throwable caught) {
                workspace.showError(caught);
            }

            public void onSuccess(Object result) {
                workspace.setGroup((GroupDTO) result);
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


    private DefaultGDesktopPane createDesktop(Widget workspacePanel) {
        DefaultGDesktopPane desktop = new DefaultGDesktopPane();
        desktop.addWidget((Widget) workspacePanel, 0, 0);
        Gwm.setOverlayLayerDisplayOnDragAction(false);
        desktop.setTheme("alphacubecustom");
        return desktop;
    }
    

}
