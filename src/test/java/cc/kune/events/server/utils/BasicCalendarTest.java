package cc.kune.events.server.utils;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
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

  protected Calendar createCal() {
    final Calendar calendar = new Calendar();
    calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);
    return calendar;
  }

  protected Date getNow() {
    final Date date = new Date(Dates.getCurrentTimeRounded());
    return date;
  }
}
