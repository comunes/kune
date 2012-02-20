package cc.kune.events.shared;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.util.Dates;

import com.bradrydzewski.gwt.calendar.client.Appointment;

public class BasicCalendarTest {

  protected static final String DESCRIPTION = "some description";
  protected static final String ID = "some id";
  protected static final String TITLE = "some title";

  protected Appointment createAppointment(final boolean allDay) {
    final Appointment initialApp = new Appointment();
    initialApp.setTitle(TITLE);
    initialApp.setDescription(DESCRIPTION);
    initialApp.setStart(getNow());
    initialApp.setEnd(getNow());
    initialApp.setId(ID);
    initialApp.setAllDay(allDay);
    return initialApp;
  }

  protected Date getNow() {
    final Date date = new Date(Dates.getCurrentTimeRounded());
    return date;
  }

}
