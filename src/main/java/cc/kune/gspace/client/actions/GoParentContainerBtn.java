package cc.kune.gspace.client.actions;

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

import com.google.inject.Inject;

public class GoParentContainerBtn extends ButtonDescriptor {

  public static class GoParentContainerAction extends AbstractExtendedAction {

    private final Session session;
    private final StateManager stateManager;

    @Inject
    public GoParentContainerAction(final Session session, final StateManager stateManager) {
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
      // NotifyUser.hideProgress();
    }

  }

  public GoParentContainerBtn(final I18nTranslationService i18n, final GoParentContainerAction action,
      final CoreResources res) {
    super(action);
    this.withToolTip(i18n.t("Go up: Open the container folder")).withIcon(res.folderGoUp()).withStyles(
        "k-btn-min, k-fl");
  }

}
