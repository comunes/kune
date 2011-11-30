package cc.kune.events.client.actions;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class Calendar7DaysViewSelectBtn extends AbstractCalendarViewSelectBtn {

  @Inject
  public Calendar7DaysViewSelectBtn(final CalendarViewSelectAction action,
      final I18nTranslationService i18n) {
    super(action, i18n.t("7 Days"), 7, CalendarViews.DAY);
  }

}
