package cc.kune.events.client.actions;

import cc.kune.common.shared.i18n.I18nTranslationService;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class CalendarMonthViewSelectBtn extends AbstractCalendarViewSelectBtn {

  @Inject
  public CalendarMonthViewSelectBtn(final CalendarViewSelectAction action,
      final I18nTranslationService i18n) {
    super(action, i18n.t("Month"), 1, CalendarViews.MONTH);
  }

}
