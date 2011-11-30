package cc.kune.events.client.viewer;

import java.util.Date;

import cc.kune.gspace.client.tool.ContentViewer;

import com.bradrydzewski.gwt.calendar.client.CalendarViews;

public interface CalendarViewer extends ContentViewer {
  void decrement();

  Date getDate();

  void goToday();

  void increment();

  void setDate(Date date);

  void setView(CalendarViews view);

  void setView(CalendarViews view, int days);
}
