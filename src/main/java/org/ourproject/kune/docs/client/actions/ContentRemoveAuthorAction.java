package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class ContentRemoveAuthorAction implements Action<String> {

    private final Session session;
    private final StateManager stateManager;

    public ContentRemoveAuthorAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final String value) {
        onContentRemoveAuthor(value);
    }

    private void onContentRemoveAuthor(final String authorShortName) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.removeAuthor(session.getUserHash(), currentState.getGroup().getShortName(),
                currentState.getDocumentId(), authorShortName, new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        stateManager.reload();
                    }
                });
    }
}
