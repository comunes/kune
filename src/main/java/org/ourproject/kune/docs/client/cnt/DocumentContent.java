
package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public interface DocumentContent extends WorkspaceComponent {

    void setContent(StateDTO content);

    void onSaved();

    void onSaveFailed();

}
