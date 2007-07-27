package org.ourproject.kune.app.client;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KuneTool;
import org.ourproject.kune.platf.client.workspace.WorkspacePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class KuneEntryPoint implements EntryPoint, WindowResizeListener {

    private DefaultGDesktopPane desktop;
    private WorkspacePanel workspace;


    public void onModuleLoad() {
        Kune kune = Kune.getInstance();

        initWorkspace(kune);
        createDesktop();
        RootPanel.get().add(desktop);
        kune.getDefaultTool().show();
        initResizeListener();
        UIObject.setVisible(DOM.getElementById("initialstatusbar"), false);
    }

    private void initResizeListener() {
        Window.addWindowResizeListener(this);
        Window.enableScrolling(false);
        onWindowResized(Window.getClientWidth(), Window.getClientHeight());
    }

    private void initWorkspace(Kune kune) {
        workspace = kune.getWorkspace();
        KuneTool[] modules = kune.getInstalledTools();
        for (int index = 0; index < modules.length; index++) {
            KuneTool module = (KuneTool) modules[index];
            workspace.addTab(module.getName());
        }
        workspace.setLogo("Vamos a ver si sale");
    }

    private void createDesktop() {
        desktop = new DefaultGDesktopPane();
        desktop.addWidget((Widget) workspace, 0, 0);
        Gwm.setOverlayLayerDisplayOnDragAction(false);
        desktop.setTheme("alphacubecustom");
    }

    public void onWindowResized(int width, int height) {
        workspace.setWidth("" + width + "px");
        workspace.setHeight("" + height + "px");
    }

}
