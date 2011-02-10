package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UnJoinGroupAction extends AbstractExtendedAction {
    public class UnJoinGroupMenuItem extends MenuItemDescriptor {
        @Inject
        public UnJoinGroupMenuItem(final UnJoinGroupAction action) {
            super(action);
        }
    }
    private final I18nTranslationService i18n;
    private final Session session;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private final StateManager stateManager;

    @Inject
    public UnJoinGroupAction(final StateManager stateManager, final Session session, final I18nTranslationService i18n,
            final CoreResources res, final Provider<SocialNetworkServiceAsync> snServiceProvider) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
        this.snServiceProvider = snServiceProvider;
        putValue(NAME, i18n.t("Do not participate anymore in this group"));
        putValue(Action.SMALL_ICON, res.del());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().unJoinGroup(session.getUserHash(), ((GroupDTO) event.getSource()).getStateToken(),
                new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                    @Override
                    public void onSuccess(final SocialNetworkDataDTO result) {
                        NotifyUser.hideProgress();
                        NotifyUser.info(i18n.t("Removed as member"));
                        stateManager.reload();
                        // in the future with user info:
                        // services.stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                        // result);
                    }
                });
    }

}