package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class RateContentAction implements Action<Double> {

    private final StateManager stateManager;
    private final Session session;

    public RateContentAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final Double value) {
        onRateContent(value);
    }

    private void onRateContent(final Double value) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.rateContent(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getDocumentId(),
                value, new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Content rated"));
                        stateManager.reload();
                    }
                });
    }
}
