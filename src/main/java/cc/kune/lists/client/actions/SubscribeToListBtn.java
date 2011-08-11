package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SubscribeToListBtn extends ButtonDescriptor {

  public static class SubscriteToListAction extends RolAction {

    private final I18nTranslationService i18n;

    @Inject
    public SubscriteToListAction(final I18nTranslationService i18n) {
      super(AccessRolDTO.Editor, true);
      this.i18n = i18n;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    }

  }

  @Inject
  public SubscribeToListBtn(final I18nTranslationService i18n, final SubscriteToListAction action,
      final CoreResources res) {
    super(action);
    withText(i18n.t("Subscribe")).withIcon(res.add()).withToolTip(
        i18n.t("Subscribe yourself to this list"));
  }
}
