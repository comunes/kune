package org.ourproject.kune.workspace.client.entityheader.maxmin;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

    private final WorkspaceSkeleton wskel;

    public MaxMinWorkspacePanel(final WorkspaceSkeleton wskel) {
        this.wskel = wskel;
    }

    public void setMaximized(final boolean maximized) {
        wskel.setMaximized(maximized);
    }
}
