package cc.kune.docs.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.perspective.MenuActionsGroup;

import com.google.inject.Inject;

public class OpenContentMenuItem extends MenuItemDescriptor {

    public static class OpenContentAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public OpenContentAction(final StateManager stateManager) {
            this.stateManager = stateManager;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.gotoStateToken((StateToken) event.getTarget());
        }

    }

    @Inject
    public OpenContentMenuItem(final I18nTranslationService i18n, final OpenContentAction action, final NavResources res) {
        super(action);
        this.withText(i18n.t("Open")).withIcon(res.go()).in(MenuActionsGroup.class);
    }

}
