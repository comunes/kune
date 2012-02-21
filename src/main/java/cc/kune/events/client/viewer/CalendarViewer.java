package cc.kune.events.client.viewer;

import java.util.Date;

import cc.kune.gspace.client.tool.ContentViewer;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;

public interface CalendarViewer extends ContentViewer {
  public static final Appointment NO_APPOINT = new Appointment();

  void addAppointment(Appointment appt);

  void decrement();

  /**
   * Gets the current Appointment that can be edit.
   * 
   * @return the appointment to edit
   */
  Appointment getAppToEdit();

  Date getDate();

  /**
   * @return if you click on the calendar, this get the date you clicked
   */
  Date getOnOverDate();

  void goToday();

  void increment();

  void removeAppointment(Appointment app);

  void setDate(Date date);

  void setView(CalendarViews view);

  void setView(CalendarViews view, int days);
}
