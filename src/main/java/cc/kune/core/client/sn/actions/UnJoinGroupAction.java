package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UnJoinGroupAction extends RolAction {

    @Inject
    public UnJoinGroupAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n,
            final CoreResources res, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final AccessRightsClientManager rightsClientManager) {
        super(stateManager, session, i18n, res, snServiceProvider, rightsClientManager, AccessRolDTO.Editor, true,
                false, true);
        putValue(NAME, i18n.t("Leave this group"));
        putValue(SHORT_DESCRIPTION, i18n.t("Do not participate anymore in this group"));
        putValue(Action.SMALL_ICON, res.del());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.askConfirmation(i18n.t("Leave this group"), i18n.t("Are you sure?"), new OnAcceptCallback() {
            @Override
            public void onSuccess() {
                NotifyUser.showProgressProcessing();
                snServiceProvider.get().unJoinGroup(session.getUserHash(), session.getCurrentState().getStateToken(),
                        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                            @Override
                            public void onSuccess(final SocialNetworkDataDTO result) {
                                NotifyUser.hideProgress();
                                NotifyUser.info(i18n.t("Removed as member"));
                                stateManager.reload();
                                // in the future with user info:
                                // stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                                // result);
                            }
                        });
            }
        });
    }

}