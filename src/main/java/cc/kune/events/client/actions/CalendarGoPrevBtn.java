package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.ToolbarStyles;

import com.google.inject.Inject;

public class CalendarGoPrevBtn extends ButtonDescriptor {

  @Inject
  public CalendarGoPrevBtn(final CalendarGoPrevAction action, final I18nTranslationService i18n) {
    super("«", action);
    this.withToolTip(i18n.t("Previous")).withStyles(ToolbarStyles.CSSBTNL);
  }

}
