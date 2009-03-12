package org.ourproject.kune.workspace.client.entityheader.maxmin;

import org.ourproject.kune.platf.client.View;

public class MaxMinWorkspacePresenter implements MaxMinWorkspace {

    private MaxMinWorkspaceView view;

    public MaxMinWorkspacePresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(MaxMinWorkspaceView view) {
        this.view = view;
    }

    public void onMaximize() {
        showMaximized(true);
    }

    public void onMinimize() {
        showMaximized(false);
    }

    private void showMaximized(boolean maximized) {
        view.setMaximized(maximized);
        view.showMaxButton(!maximized);
    }
}
