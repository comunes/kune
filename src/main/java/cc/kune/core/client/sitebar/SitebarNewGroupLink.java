package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SitebarNewGroupLink extends ButtonDescriptor {

    public static class SitebarNewGroupAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public SitebarNewGroupAction(final StateManager stateManager, final I18nTranslationService i18n) {
            super();
            this.stateManager = stateManager;
            putValue(Action.NAME, i18n.t("Create New Group"));
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.gotoToken(SiteCommonTokens.NEWGROUP);
        }

    }

    @Inject
    public SitebarNewGroupLink(final SitebarNewGroupAction action) {
        super(action);
        setStyles("k-no-backimage, k-btn-sitebar");
        setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
    }
}
