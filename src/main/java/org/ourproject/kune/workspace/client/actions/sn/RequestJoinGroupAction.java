package org.ourproject.kune.workspace.client.actions.sn;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

@SuppressWarnings("unchecked")
public class RequestJoinGroupAction implements Action {

    private final Session session;
    private final StateManager stateManager;

    public RequestJoinGroupAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final Object value) {
        onRequestJoinGroup();
    }

    private void onRequestJoinGroup() {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.requestJoinGroup(session.getUserHash(), session.getCurrentState().getGroup().getShortName(),
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        final String resultType = (String) result;
                        if (resultType == SocialNetworkDTO.REQ_JOIN_ACEPTED) {
                            Site.info(Kune.I18N.t("You are now member of this group"));
                            stateManager.reload();
                        }
                        if (resultType == SocialNetworkDTO.REQ_JOIN_DENIED) {
                            Site.important(Kune.I18N.t("Sorry this is a closed group"));
                        }
                        if (resultType == SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION) {
                            Site.info(Kune.I18N.t("Requested. Waiting for admins decision"));
                        }
                    }
                });
    }

}
