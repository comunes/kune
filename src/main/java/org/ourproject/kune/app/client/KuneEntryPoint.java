package org.ourproject.kune.app.client;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.chat.client.ChatTool;
import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.home.client.HomeTool;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KuneTool;
import org.ourproject.kune.platf.client.workspace.Workspace;
import org.ourproject.kune.platf.client.workspace.WorkspacePresenter;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;
 
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class KuneEntryPoint implements EntryPoint, WindowResizeListener {
    private DefaultGDesktopPane desktop;
    private WorkspacePanel workspacePanel;

    public void onModuleLoad() {
        Kune kune = Kune.getInstance();

        initWorkspace(kune);
        createDesktop();
        RootPanel.get().add(desktop);
        initResizeListener();
        UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);
    }
    
    public KuneTool[] registerTools() {
        KuneTool[] registeredTools = new KuneTool[] {
                new HomeTool(),
                new DocumentTool(), 
                new ChatTool()
        };
        
        return registeredTools;
    }

    private void initResizeListener() {
        Window.addWindowResizeListener(this);
        Window.enableScrolling(false);
        onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    }

    private void initWorkspace(Kune kune) {
        Workspace workspace = new Workspace();
        workspace.setGroupName("Vamos a ver si sale");
        workspace.setTools(registerTools());
        WorkspacePresenter workspacePresenter = new WorkspacePresenter(workspace);
        workspacePanel = new WorkspacePanel(workspacePresenter);
        workspacePresenter.init(workspacePanel);
    }

    private void createDesktop() {
        desktop = new DefaultGDesktopPane();
        desktop.addWidget((Widget) workspacePanel, 0, 0);
        Gwm.setOverlayLayerDisplayOnDragAction(false);
        desktop.setTheme("alphacubecustom");
    }

    public void onWindowResized(int width, int height) {
        workspacePanel.setWidth("" + width + "px");
        workspacePanel.setHeight("" + height + "px");
    }

}
