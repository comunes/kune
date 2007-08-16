package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface ClientTool {
    String getName();

    ToolTrigger getTrigger();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

    void setContent(StateDTO content);

}
