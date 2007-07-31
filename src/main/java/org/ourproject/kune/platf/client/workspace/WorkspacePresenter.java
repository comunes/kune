package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.Kune;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dto.ContentTreeDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class WorkspacePresenter {
    private final KunePlatform platform;
    private final WorkspaceView view;
    private Tool currentTool;
    private int currentToolIndex;

    public WorkspacePresenter(KunePlatform platform, WorkspaceView view) {
        this.platform = platform;
        this.view = view;
    }

    public void showError(Throwable caught) {

    }

    public void setGroup(GroupDTO group) {
        view.setLogo("group name here");
    }

    public void setSelectedTool(int index) {
        if (currentToolIndex != index) {
            currentToolIndex = index;
            currentTool = platform.getTool(index);
            view.setSelectedTab(index);
            currentTool.provider.getContentTree(Kune.getInstance().getUserHash(), new AsyncCallback() {
                public void onFailure(Throwable arg0) {
                }

                public void onSuccess(Object result) {
                    ContentTreeDTO tree = (ContentTreeDTO) result;
                    view.setContextMenu((Widget) currentTool.factory.getContextView(tree));
                    // TODO:
                    view.setContent((Widget) currentTool.factory.getContentView(null));
                }
            });
        }
    }
}
