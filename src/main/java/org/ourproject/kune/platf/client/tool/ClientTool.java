
package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface ClientTool {
    String getName();

    ToolTrigger getTrigger();

    WorkspaceComponent getContext();

    WorkspaceComponent getContent();

    void setContent(StateDTO state);

    void setContext(StateDTO state);

    void setGroupState(String groupShortName);

}
