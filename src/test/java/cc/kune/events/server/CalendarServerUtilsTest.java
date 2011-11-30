package cc.kune.events.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.Dates;

import org.junit.Ignore;
import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.Appointment;

public class CalendarServerUtilsTest {

  private static final String DESCRIPTION = "some description";
  private static final String ID = "some id";
  private static final String TITLE = "some title";

  private void checkConversionFromAndTo(final Appointment initialApp, final boolean allDay) {
    final VEvent vevent = CalendarServerUtils.from(initialApp);
    assertEquals(initialApp.getDescription(), vevent.getDescription().getValue());
    assertEquals(initialApp.getTitle(), vevent.getSummary().getValue());
    if (allDay) {
      final Date start = initialApp.getStart();
      start.setHours(0);
      start.setMinutes(0);
      start.setSeconds(0);
      assertEquals(start, new Date(vevent.getStartDate().getDate().getTime()));
      assertEquals(initialApp.getEnd(), vevent.getEndDate().getDate());
    } else {
      assertEquals(initialApp.getStart().getTime(), vevent.getStartDate().getDate().getTime());
      assertEquals(initialApp.getEnd().getTime(), vevent.getEndDate().getDate().getTime());
    }
    assertEquals(allDay, initialApp.isAllDay());
    assertEquals(allDay, !(vevent.getStartDate().getDate() instanceof DateTime));
    // final Uid uid = vevent.getUid();
    // assertEquals(initialApp.getId(), uid != null ? uid.getValue() : null);
    final Appointment reconvertedApp = CalendarServerUtils.to(vevent);
    assertEquals(initialApp.getTitle(), reconvertedApp.getTitle());
    assertEquals(initialApp.getDescription(), reconvertedApp.getDescription());
    // assertEquals(initialApp.getId(), endApp.getId());
    assertEquals(allDay, reconvertedApp.isAllDay());
    assertEquals(initialApp.getStart().getTime(), reconvertedApp.getStart().getTime());
    assertEquals(initialApp.getEnd().getTime(), reconvertedApp.getEnd().getTime());
    assertTrue(initialApp.compareTo(reconvertedApp) == 0);
  }

  @Test
  public void testFromToFrom() {
    final Appointment initialApp = new Appointment();
    initialApp.setTitle(TITLE);
    initialApp.setDescription(DESCRIPTION);
    initialApp.setStart(new Date(Dates.getCurrentTimeRounded()));
    initialApp.setEnd(new Date(Dates.getCurrentTimeRounded()));
    initialApp.setId(ID);
    final boolean allDay = false;
    initialApp.setAllDay(allDay);

    checkConversionFromAndTo(initialApp, allDay);
  }

  @Ignore
  @Test
  public void testFromToFromAllDay() {
    final Appointment initialApp = new Appointment();
    initialApp.setTitle(TITLE);
    initialApp.setDescription(DESCRIPTION);
    initialApp.setStart(new Date(Dates.getCurrentTimeRounded()));
    initialApp.setEnd(new Date(Dates.getCurrentTimeRounded()));
    initialApp.setId(ID);
    final boolean allDay = true;
    initialApp.setAllDay(allDay);

    checkConversionFromAndTo(initialApp, allDay);
  }

}
