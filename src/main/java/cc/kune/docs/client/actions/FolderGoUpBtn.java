package cc.kune.docs.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.perspective.ViewActionsGroup;

import com.google.inject.Inject;

public class FolderGoUpBtn extends ButtonDescriptor {

    public static class FolderGoUpAction extends AbstractExtendedAction {

        private final Session session;
        private final StateManager stateManager;

        @Inject
        public FolderGoUpAction(final Session session, final StateManager stateManager) {
            this.session = session;
            this.stateManager = stateManager;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            NotifyUser.showProgress();
            StateToken stateToken;
            final StateAbstractDTO state = session.getCurrentState();
            if (state instanceof StateContentDTO) {
                stateToken = ((StateContentDTO) state).getContainer().getStateToken();
            } else {
                final ContainerDTO container = ((StateContainerDTO) state).getContainer();
                stateToken = container.getStateToken().copy().setFolder(container.getParentFolderId());
            }
            stateManager.gotoStateToken(stateToken);
            NotifyUser.hideProgress();
        }

    }

    @Inject
    public FolderGoUpBtn(final I18nTranslationService i18n, final FolderGoUpAction action, final CoreResources res) {
        super(action);
        this.withToolTip(i18n.t("Go up: Open the container folder")).withIcon(res.folderGoUp()).in(
                ViewActionsGroup.class);
    }

}
