package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.UserBuddiesVisibility;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserSNVisibilityAction extends AbstractExtendedAction {
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<UserServiceAsync> userServiceAsync;
    private UserBuddiesVisibility visibility;

    @Inject
    public UserSNVisibilityAction(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final Provider<UserServiceAsync> userServiceProvider) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.userServiceAsync = userServiceProvider;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        userServiceAsync.get().setBuddiesVisibility(session.getUserHash(),
                session.getCurrentState().getGroup().getStateToken(), visibility, new AsyncCallbackSimple<Void>() {
                    @Override
                    public void onSuccess(final Void result) {
                        NotifyUser.info(i18n.t("Visibility of your network changed to " + visibility.toString()));
                        // NotifyUser.info(i18n.t("Visibility of your network changed"));
                        stateManager.reload();
                    }
                });

    }

    public void setVisibility(final UserBuddiesVisibility visibility) {
        this.visibility = visibility;
    }

}
