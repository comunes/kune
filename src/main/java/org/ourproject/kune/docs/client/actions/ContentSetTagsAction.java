package org.ourproject.kune.docs.client.actions;

import java.util.List;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class ContentSetTagsAction implements Action<String> {

    private final Session session;
    private final Workspace workspace;

    public ContentSetTagsAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final String value) {
        onContentsetTags(value);
    }

    private void onContentsetTags(final String tags) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.setTags(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getDocumentId(),
                tags, new AsyncCallbackSimple<List<TagResultDTO>>() {
                    public void onSuccess(final List<TagResultDTO> result) {
                        workspace.getTagsComponent().setGroupTags(result);
                        Site.hideProgress();
                    }
                });
    }
}
