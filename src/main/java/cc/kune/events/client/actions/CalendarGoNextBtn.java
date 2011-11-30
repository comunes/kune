package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.ToolbarStyles;

import com.google.inject.Inject;

public class CalendarGoNextBtn extends ButtonDescriptor {

  @Inject
  public CalendarGoNextBtn(final CalendarGoNextAction action, final I18nTranslationService i18n) {
    super("»", action);
    this.withToolTip(i18n.t("Next")).withStyles(ToolbarStyles.CSSBTNR);
  }

}
