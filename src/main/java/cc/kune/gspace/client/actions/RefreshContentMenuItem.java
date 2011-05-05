package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class RefreshContentMenuItem extends MenuItemDescriptor {

    public static class GoParentContainerAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public GoParentContainerAction(final StateManager stateManager) {
            this.stateManager = stateManager;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.refreshCurrentGroupState();
        }

    }

    @Inject
    public RefreshContentMenuItem(final I18nTranslationService i18n, final GoParentContainerAction action,
            final ContentViewerOptionsMenu optionsMenu, final NavResources res) {
        super(action);
        this.withText(i18n.t("Reload current page")).withIcon(res.refresh()).withParent(optionsMenu);
    }

}
