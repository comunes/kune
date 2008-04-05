package org.ourproject.kune.workspace.client.actions.sn;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class AcceptJoinGroupAction implements Action<String> {

    private final StateManager stateManager;
    private final Workspace workspace;
    private final Session session;

    public AcceptJoinGroupAction(final Session session, final StateManager stateManager, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
        this.stateManager = stateManager;
    }

    public void execute(final String groupShortName) {
        onAcceptJoinGroup(groupShortName);
    }

    private void onAcceptJoinGroup(final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.AcceptJoinGroup(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
                groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                    public void onSuccess(final SocialNetworkResultDTO result) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Member accepted"));
                        stateManager.setSocialNetwork(result);
                        workspace.getGroupMembersComponent().showCollabs();
                    }
                });

    }
}
