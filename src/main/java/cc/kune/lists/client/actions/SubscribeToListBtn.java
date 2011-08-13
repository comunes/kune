package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SubscribeToListBtn extends ButtonDescriptor {

  @Inject
  public SubscribeToListBtn(final I18nTranslationService i18n, final SubscriteToListAction action,
      final Session session, final CoreResources res) {
    super(action);
    final Boolean areYouMember = session.isLogged()
        && session.getContainerState().getAccessLists().getEditors().getList().contains(
            session.getCurrentUserInfo().getUserGroup());
    action.putValue(SubscriteToListAction.ISMEMBER, areYouMember);
    if (!areYouMember) {
      withText(i18n.t("Subscribe"));
      withIcon(res.add());
      withToolTip(i18n.t("Subscribe to this list"));
    } else {
      withText(i18n.t("Unsubscribe"));
      withIcon(res.remove());
      withToolTip(i18n.t("Unsubscribe to this list"));
    }
  }
}
