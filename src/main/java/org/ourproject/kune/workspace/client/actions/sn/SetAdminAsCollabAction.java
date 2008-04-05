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

public class SetAdminAsCollabAction implements Action<String> {

    private final Session session;
    private final StateManager stateManager;
    private final Workspace workspace;

    public SetAdminAsCollabAction(final Session session, final StateManager stateManager, final Workspace workspace) {
        this.session = session;
        this.stateManager = stateManager;
        this.workspace = workspace;
    }

    public void execute(final String value) {
        onSetAdminAsCollab(value);
    }

    private void onSetAdminAsCollab(final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.setAdminAsCollab(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
                groupShortName, new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                    public void onSuccess(final SocialNetworkResultDTO result) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Type of member changed"));
                        stateManager.reload();
                        workspace.getGroupMembersComponent().showCollabs();
                    }
                });
    }
}
