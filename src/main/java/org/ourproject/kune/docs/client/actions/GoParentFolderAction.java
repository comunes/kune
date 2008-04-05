
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

@SuppressWarnings("unchecked")
public class GoParentFolderAction implements Action {
    private final StateManager stateManager;
    private final Session session;

    public GoParentFolderAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final Object value) {
        goParent(session.getCurrentState(), stateManager);
    }

    private void goParent(final StateDTO state, final StateManager stateManager) {
        StateToken token = state.getStateToken();
        token.setDocument(null);
        token.setFolder(state.getFolder().getParentFolderId().toString());
        stateManager.setState(token);
    }

}
