package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.gspace.client.viewers.ToolbarStyles;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;

public class AbstractCalendarViewSelectBtn extends ButtonDescriptor {

  public AbstractCalendarViewSelectBtn(final CalendarViewSelectAction action, final String text,
      final int days, final CalendarViews view) {
    super(text, action);
    action.setDays(days);
    action.setView(view);
    this.withStyles(ToolbarStyles.CSSBTNC);
  }

}
