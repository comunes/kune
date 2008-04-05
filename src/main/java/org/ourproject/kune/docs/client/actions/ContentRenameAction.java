package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentRenameAction implements Action<ParamCallback<String, String>> {

    private final Session session;

    public ContentRenameAction(final Session session) {
        this.session = session;
    }

    public void execute(final ParamCallback<String, String> paramCall) {
        onContentRename(paramCall.getParam(), paramCall.getCallback());
    }

    private void onContentRename(final String newName, final AsyncCallback<String> callback) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        StateDTO currentState = session.getCurrentState();
        server.rename(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getStateToken()
                .getEncoded(), newName, callback);
        Site.hideProgress();
    }
}
