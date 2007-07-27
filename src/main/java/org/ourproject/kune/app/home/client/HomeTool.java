package org.ourproject.kune.app.home.client;

import org.ourproject.kune.app.home.client.rpc.HomeService;
import org.ourproject.kune.app.home.client.rpc.HomeServiceAsync;
import org.ourproject.kune.platf.client.AbstractTool;
import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KuneTool;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class HomeTool extends AbstractTool implements KuneTool {
    private HomeServiceAsync server;
    private Home home;

    public HomeTool() {
        server = HomeService.App.getInstance();
    }
    
    public String getName() {
        return "Home";
    }

    public void show() {
        if (home == null) loadHome();
        updateWorkspace();
    }

    private void loadHome() {
        server.loadHome(Kune.getUserHash(), new AsyncCallback () {
            public void onFailure(Throwable t) {
            }

            public void onSuccess(Object result) {
                home = (Home) result;
                updateWorkspace();
            }
        });
    }

    protected void updateWorkspace() {
        workspaceView.setContent((Widget) HomeViewFactory.getMainView());
        workspaceView.setContextMenu((Widget) HomeViewFactory.getContextMenuView());
    }

}
