package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkRequestResult;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JoinGroupAction extends RolAction {

    @Inject
    public JoinGroupAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n,
            final CoreResources res, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final AccessRightsClientManager rightsClientManager) {
        super(stateManager, session, i18n, res, snServiceProvider, rightsClientManager, AccessRolDTO.Viewer, false,
                true, false);
        putValue(NAME, i18n.t("Participate"));
        putValue(SHORT_DESCRIPTION, i18n.t("Request to participate in this group"));
        putValue(Action.SMALL_ICON, res.addGreen());
        putValue(Action.STYLES, "k-sn-join");
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().requestJoinGroup(session.getUserHash(), session.getCurrentState().getStateToken(),
                new AsyncCallbackSimple<SocialNetworkRequestResult>() {
                    @Override
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