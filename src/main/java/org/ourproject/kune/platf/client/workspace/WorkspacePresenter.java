package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public class WorkspacePresenter {
    private final WorkspaceView view;
    private Tool currentTool;
    private int currentToolIndex;

    public WorkspacePresenter(WorkspaceView view) {
        this.view = view;
    }

    public void showError(Throwable caught) {

    }

    public void setGroup(GroupDTO group) {
        view.setLogo("group name here");
    }

    public void setSelectedTool(int index) {
        if (currentToolIndex != index) {
//            currentToolIndex = index;
//            currentTool = platform.getTool(index);
//            view.setSelectedTab(index);
//            currentTool.provider.getContentTree(Kune.getInstance().getUserHash(), new AsyncCallback() {
//                public void onFailure(Throwable arg0) {
//                }
//
//                public void onSuccess(Object result) {
//                    ContentTreeDTO tree = (ContentTreeDTO) result;
//                    view.setContextMenu((Widget) currentTool.factory.getContextView(tree));
//                    // TODO:
//                    view.setContent((Widget) currentTool.factory.getContentView(null));
//                }
//            });
        }
    }
}
