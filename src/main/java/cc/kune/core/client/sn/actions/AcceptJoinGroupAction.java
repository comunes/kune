package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
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

public class AcceptJoinGroupAction extends AbstractExtendedAction {
    private final I18nTranslationService i18n;
    private final Session session;
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;

    private final StateManager stateManager;

    @Inject
    public AcceptJoinGroupAction(final StateManager stateManager, final Session session,
            final I18nTranslationService i18n, final CoreResources res,
            final Provider<SocialNetworkServiceAsync> snServiceProvider) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
        this.snServiceProvider = snServiceProvider;
        putValue(NAME, i18n.t("Accept this member"));
        putValue(Action.SMALL_ICON, res.accept());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.showProgressProcessing();
        snServiceProvider.get().acceptJoinGroup(session.getUserHash(), session.getCurrentState().getStateToken(),
                ((GroupDTO) event.getTarget()).getShortName(), new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                    @Override
                    public void onSuccess(final SocialNetworkDataDTO result) {
                        NotifyUser.hideProgress();
                        NotifyUser.info(i18n.t("Member accepted"));
                        stateManager.setSocialNetwork(result);
                    }
                });
    }

}