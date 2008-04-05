
package org.ourproject.kune.chat.client.cnt;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface ChatContent extends WorkspaceComponent {
    void setState(StateDTO state);

}
