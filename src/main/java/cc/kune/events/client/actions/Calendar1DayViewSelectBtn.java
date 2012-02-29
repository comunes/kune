package cc.kune.events.client.actions;

import cc.kune.common.shared.i18n.I18nTranslationService;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class Calendar1DayViewSelectBtn extends AbstractCalendarViewSelectBtn {

  @Inject
  public Calendar1DayViewSelectBtn(final CalendarViewSelectAction action,
      final I18nTranslationService i18n) {
    super(action, i18n.t("[%d] Day", 1), 1, CalendarViews.DAY);
  }

}
