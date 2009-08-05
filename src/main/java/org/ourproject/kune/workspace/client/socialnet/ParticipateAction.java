package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.state.AccessRightsClientManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.ioc.Provider;

public class ParticipateAction extends RolAction {
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;

    public ParticipateAction(final Session session, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final StateManager stateManager, final AccessRightsClientManager rightsManager,
            final I18nTranslationService i18n, final ImgResources imgResources) {
        super(session, stateManager, rightsManager, i18n, AccessRolDTO.Viewer, i18n.t("Participate"),
                i18n.t("Request to participate in this group"), imgResources.addGreen());
        this.snServiceProvider = snServiceProvider;
        super.setVisible(false, true);
        super.setMustBeAuthenticated(false);
    }

    public void actionPerformed(final ActionEvent event) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().requestJoinGroup(session.getUserHash(), session.getCurrentState().getStateToken(),
                new AsyncCallbackSimple<SocialNetworkRequestResult>() {
                    public void onSuccess(final SocialNetworkRequestResult result) {
                        NotifyUser.hideProgress();
                        switch ((result)) {
                        case accepted:
                            NotifyUser.info(i18n.t("You are now member of this group"));
                            stateManager.reload();
                            break;
                        case denied:
                            NotifyUser.important(i18n.t("Sorry this is a closed group"));
                            break;
                        case moderated:
                            NotifyUser.info(i18n.t("Membership requested. Waiting for admins decision"));
                            break;
                        default:
                            NotifyUser.info(i18n.t("Programatic error in ParticipateAction"));
                        }
                    }
                });
    }
}
