package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.dto.GroupDTO;

public interface Workspace {
    public void showError(Throwable caught);
    public void setGroup(GroupDTO group);
    public void setTool(String toolName);
    public void setContext(WorkspaceComponent contextComponent);
    public void setContent(WorkspaceComponent contentComponent);

}