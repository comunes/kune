
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddDocumentAction implements Action<String> {

    private final Session session;
    private final StateManager stateManager;

    public AddDocumentAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final String name) {
        addDocument(name, session.getCurrentState().getFolder());
    }

    private void addDocument(final String name, final ContainerDTO containerDTO) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addContent(session.getUserHash(), session.getCurrentState().getGroup().getShortName(), containerDTO
                .getId(), name, new AsyncCallbackSimple<StateDTO>() {
            public void onSuccess(final StateDTO state) {
                Site.hideProgress();
                Site.info(Kune.I18N.t("Created, now you can edit the document"));
                stateManager.setRetrievedState(state);
            }
        });
    }
}
