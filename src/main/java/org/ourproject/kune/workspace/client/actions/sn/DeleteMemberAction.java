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

public class DeleteMemberAction implements Action<String> {
    private final Session session;
    private final StateManager stateManager;

    public DeleteMemberAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final String value) {
        onDeleteMember(value);
    }

    private void onDeleteMember(final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.deleteMember(session.getUserHash(), session.getCurrentState().getGroup().getShortName(), groupShortName,
                new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                    public void onSuccess(final SocialNetworkResultDTO result) {
                        Site.hideProgress();
                        Site.info(Kune.I18N.t("Member removed"));
                        stateManager.reload();
                        // in the future, only if I cannot be affected:
                        // services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                        // result);
                    }
                });

    }
}
