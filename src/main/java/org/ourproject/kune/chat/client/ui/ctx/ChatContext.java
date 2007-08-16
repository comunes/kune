package org.ourproject.kune.chat.client.ui.ctx;

import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public interface ChatContext extends WorkspaceComponent {

    void setState(StateDTO state);

}
