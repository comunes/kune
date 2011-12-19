package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.ToolbarStyles;

import com.google.inject.Inject;

public class CalendarGoTodayBtn extends ButtonDescriptor {

  @Inject
  public CalendarGoTodayBtn(final CalendarGoTodayAction action, final I18nTranslationService i18n) {
    super(i18n.t("Today"), action);
    this.withToolTip(i18n.t("Go To Today")).withStyles(ToolbarStyles.CSSBTN);
  }

}
