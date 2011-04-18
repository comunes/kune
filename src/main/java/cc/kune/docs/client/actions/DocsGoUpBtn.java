package cc.kune.docs.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.perspective.ViewPerspective;

import com.google.inject.Inject;

public class DocsGoUpBtn extends ButtonDescriptor {

    public static class DocsGoUpAction extends AbstractExtendedAction {

        private final Session session;
        private final StateManager stateManager;

        @Inject
        public DocsGoUpAction(final Session session, final StateManager stateManager) {
            this.session = session;
            this.stateManager = stateManager;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
        }

    }

    @Inject
    public DocsGoUpBtn(final I18nTranslationService i18n, final DocsGoUpAction action, final CoreResources res) {
        super(action);
        this.withToolTip(i18n.t("Go up: Open the container folder")).withIcon(res.folderGoUp()).in(
                ViewPerspective.class);
    }

}
