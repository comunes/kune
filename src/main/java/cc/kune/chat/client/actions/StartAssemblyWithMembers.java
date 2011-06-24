package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class StartAssemblyWithMembers extends MenuItemDescriptor {

  @Inject
  public StartAssemblyWithMembers(final OpenGroupPublicChatRoomAction action,
      final I18nTranslationService i18n) {
    super(action);
    action.setInviteMembers(true);
    withText(i18n.t("Start a public assembly with members")).withToolTip(
        i18n.t("Enter to this group public chat room and invite members"));
    setParent(GroupSNConfActions.OPTIONS_MENU);
    setPosition(0);
  }
}
