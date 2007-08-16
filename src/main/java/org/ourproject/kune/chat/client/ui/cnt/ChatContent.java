package org.ourproject.kune.chat.client.ui.cnt;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface ChatContent extends WorkspaceComponent {
    void setState(StateDTO state);

}
