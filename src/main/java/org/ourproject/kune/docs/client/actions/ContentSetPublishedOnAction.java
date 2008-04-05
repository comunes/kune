package org.ourproject.kune.docs.client.actions;

import java.util.Date;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class ContentSetPublishedOnAction implements Action<Date> {

    private final Workspace workspace;
    private final Session session;

    public ContentSetPublishedOnAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final Date value) {
        onContentsetPublishedOn(value);
    }

    private void onContentsetPublishedOn(final Date publishedOn) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.setPublishedOn(session.getUserHash(), currentState.getGroup().getShortName(), currentState
                .getDocumentId(), publishedOn, new AsyncCallbackSimple<Object>() {
            public void onSuccess(final Object result) {
                Site.hideProgress();
                workspace.getContentTitleComponent().setContentDate(publishedOn);
            }
        });
    }
}
