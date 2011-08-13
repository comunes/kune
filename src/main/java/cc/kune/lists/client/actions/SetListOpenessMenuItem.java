package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SetListOpenessMenuItem extends MenuItemDescriptor {

  @Inject
  public SetListOpenessMenuItem(final I18nTranslationService i18n, final SetListOpenessAction action,
      final Session session, final CoreResources res, final OptionsListMenu menu) {
    super(action);
    setParent(menu, false);
    final Boolean isPublic = session.getContainerState().getAccessLists().getViewers().getMode().equals(
        GroupListDTO.EVERYONE);
    action.putValue(SetListOpenessAction.ISPUBLIC, isPublic);
    if (!isPublic) {
      withText(i18n.t("Make this list public"));
      withIcon(res.everybody());
    } else {
      withText(i18n.t("Make this list not public"));
      withIcon(res.nobody());
    }
  }
}
