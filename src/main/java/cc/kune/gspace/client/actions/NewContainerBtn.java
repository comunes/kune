package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewContainerBtn extends ButtonDescriptor {

  public static class NewContainerAction extends RolAction {

    private final ContentCache cache;
    private final Provider<ContentServiceAsync> contentService;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public NewContainerAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService,
        final ContentCache cache) {
      super(AccessRolDTO.Editor, true);
      this.session = session;
      this.stateManager = stateManager;
      this.i18n = i18n;
      this.contentService = contentService;
      this.cache = cache;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.showProgressProcessing();
      stateManager.gotoStateToken(((HasContent) session.getCurrentState()).getContainer().getStateToken());
      contentService.get().addFolder(session.getUserHash(), session.getCurrentStateToken(),
          (String) getValue(NEW_NAME), (String) getValue(ID),
          new AsyncCallbackSimple<StateContainerDTO>() {
            @Override
            public void onSuccess(final StateContainerDTO state) {
              // contextNavigator.setEditOnNextStateChange(true);
              stateManager.setRetrievedStateAndGo(state);
              NotifyUser.hideProgress();
            }
          });
      cache.removeContent(session.getCurrentStateToken());
    }

  }

  private static final String ID = "ctnernewid";
  private static final String NEW_NAME = "ctnernewname";

  public NewContainerBtn(final I18nTranslationService i18n, final NewContainerAction action,
      final ImageResource icon, final String title, final String tooltip, final String newName,
      final String id) {
    super(action);
    // The name given to this new content
    action.putValue(NEW_NAME, newName);
    action.putValue(ID, id);
    this.withText(title).withToolTip(tooltip).withIcon(icon).withStyles("k-def-docbtn, k-fr");
  }
}
