package org.ourproject.kune.app.client;

import org.gwm.client.impl.DefaultGDesktopPane;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KuneTool;
import org.ourproject.kune.platf.client.workspace.WorkspaceView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class KuneEntryPoint implements EntryPoint, WindowResizeListener {

    private DefaultGDesktopPane desktop;
    private WorkspaceView workspace;

    public void onModuleLoad() {
	Kune kune = Kune.getInstance();
	workspace = kune.getWorkspace();

	KuneTool[] modules = kune.getInstalledTools();
	for (int index = 0; index < modules.length; index++) {
	    KuneTool module = (KuneTool) modules[index];
	    workspace.addTab(module.getName());
	}
	
        desktop = new DefaultGDesktopPane();
	desktop.addWidget((Widget) workspace, 0, 0);

	Window.addWindowResizeListener(this);
        Window.enableScrolling(false);
        onWindowResized(Window.getClientWidth(), Window.getClientHeight());

        kune.getDefaultTool().show();
        
	RootPanel.get().add(desktop);
    }

    public void onWindowResized(int width, int height) {
    }
}
