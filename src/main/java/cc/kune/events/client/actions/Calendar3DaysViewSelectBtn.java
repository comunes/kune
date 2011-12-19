package cc.kune.events.client.actions;

import cc.kune.common.shared.i18n.I18nTranslationService;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.inject.Inject;

public class Calendar3DaysViewSelectBtn extends AbstractCalendarViewSelectBtn {

  @Inject
  public Calendar3DaysViewSelectBtn(final CalendarViewSelectAction action,
      final I18nTranslationService i18n) {
    super(action, i18n.t("3 Days"), 3, CalendarViews.DAY);
  }

}
