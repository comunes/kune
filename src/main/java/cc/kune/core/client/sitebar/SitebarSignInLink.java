package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class SitebarSignInLink extends ButtonDescriptor {

    public static class SitebarSignInAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public SitebarSignInAction(final StateManager stateManager, final I18nTranslationService i18n) {
            super();
            this.stateManager = stateManager;

            putValue(Action.NAME, i18n.t("Sign in to collaborate"));
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.gotoToken(SiteCommonTokens.SIGNIN);
        }

    }

    public static final String SITE_SIGN_IN = "kune-ssilp-hy";

    @Inject
    public SitebarSignInLink(final SitebarSignInAction action, final EventBus eventBus, final Session session) {
        super(action);
        setStyles("k-no-backimage, k-btn-sitebar");
        setId(SITE_SIGN_IN);
        setVisible(!session.isLogged());
        eventBus.addHandler(UserSignInEvent.getType(), new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                SitebarSignInLink.this.setVisible(false);
            }
        });
        eventBus.addHandler(UserSignOutEvent.getType(), new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                SitebarSignInLink.this.setVisible(true);
            }
        });
    }
}
