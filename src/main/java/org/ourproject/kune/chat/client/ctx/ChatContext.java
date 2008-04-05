
package org.ourproject.kune.chat.client.ctx;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface ChatContext extends WorkspaceComponent {

    void setState(StateDTO state);

}
