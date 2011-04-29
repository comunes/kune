package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SetAsHomePageMenuItem extends MenuItemDescriptor {

  public static class SetAsHomePageAction extends RolAction {

    private final Provider<ContentServiceAsync> contentService;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public SetAsHomePageAction(final Session session,
        final Provider<ContentServiceAsync> contentService, final I18nTranslationService i18n) {
      super(AccessRolDTO.Administrator, true);
      this.session = session;
      this.contentService = contentService;
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final StateToken token = (StateToken) event.getTarget();
      NotifyUser.showProgressProcessing();
      contentService.get().setAsDefaultContent(session.getUserHash(), token,
          new AsyncCallbackSimple<ContentSimpleDTO>() {
            @Override
            public void onSuccess(final ContentSimpleDTO defContent) {
              session.getCurrentState().getGroup().setDefaultContent(defContent);
              NotifyUser.hideProgress();
              NotifyUser.info(i18n.t("Selected as the homepage"));
            }
          });
    }

  }

  @Inject
  public SetAsHomePageMenuItem(final I18nTranslationService i18n, final SetAsHomePageAction action,
      final CoreResources res) {
    super(action);
    this.withText(i18n.t("Delete")).withIcon(res.groupHome()).withText(i18n.t("Select as the homepage"));
  }

}
