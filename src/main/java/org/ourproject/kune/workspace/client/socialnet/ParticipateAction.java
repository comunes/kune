package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.core.shared.i18n.I18nTranslationService;

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
