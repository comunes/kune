package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class OpenRoomBtn extends ButtonDescriptor {

  @Inject
  public OpenRoomBtn(final I18nTranslationService i18n, final OpenChatAction action,
      final NavResources res) {
    super(action);
    this.withText(i18n.t("Enter to this room")).withIcon(res.room()).withStyles("k-def-docbtn, k-fr");
  }
}