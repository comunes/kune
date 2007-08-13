package org.ourproject.kune.workspace.client;

import java.util.Iterator;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;

public interface Workspace {
    public void showError(Throwable caught);

    public void showGroup(GroupDTO group);

    public void setTool(String toolName);

    public void setContext(WorkspaceComponent contextComponent);

    public void setContent(WorkspaceComponent contentComponent);

    public void attachTools(Iterator iterator);

    public View getView();

    public void adjustSize(int windowWidth, int clientHeight);

}