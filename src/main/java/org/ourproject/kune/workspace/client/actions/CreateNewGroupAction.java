package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.state.Session;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CreateNewGroupAction implements Action<ParamCallback<GroupDTO, StateToken>> {

    private final Session session;

    public CreateNewGroupAction(final Session session) {
        this.session = session;
    }

    public void execute(final ParamCallback<GroupDTO, StateToken> paramCall) {
        onNewGroup(paramCall.getParam(), paramCall.getCallback());
    }

    private void onNewGroup(final GroupDTO group, final AsyncCallback<StateToken> callback) {
        GroupServiceAsync groupService = GroupService.App.getInstance();
        groupService.createNewGroup(session.getUserHash(), group, callback);
    }
}
