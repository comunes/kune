/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.events.server.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;

import org.junit.Ignore;
import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.Appointment;

public class EventsServerConversionUtilTest extends BasicCalendarTest {
  protected static final String DESCRIPTION = "some description";
  protected static final String ID = "some id";
  protected static final String TITLE = "some title";

  @SuppressWarnings("deprecation")
  private void checkConversionFromAndTo(final Appointment initialApp, final boolean allDay)
      throws IOException, ValidationException {
    final VEvent vevent = EventsServerConversionUtil.toVEvent(initialApp);
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
    assertEquals(initialApp.getId(), vevent.getUid().getValue());
    final Calendar cal = createCal();
    cal.getComponents().add(vevent);

    final OutputStream out = System.out;
    final CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(cal, out);

    final Appointment reconvertedApp = EventsServerConversionUtil.to(vevent);
    assertEquals(initialApp.getTitle(), reconvertedApp.getTitle());
    assertEquals(initialApp.getDescription(), reconvertedApp.getDescription());
    assertEquals(initialApp.getId(), reconvertedApp.getId());
    assertEquals(allDay, reconvertedApp.isAllDay());
    assertEquals(initialApp.getStart().getTime(), reconvertedApp.getStart().getTime());
    assertEquals(initialApp.getEnd().getTime(), reconvertedApp.getEnd().getTime());
    assertTrue(initialApp.compareTo(reconvertedApp) == 0);
  }

  private void convertAndTest(final Appointment app, final boolean allDay) throws Exception {
    final Map<String, String> map = EventsServerConversionUtil.toMap(app);
    final Appointment appReConverted = EventsServerConversionUtil.toApp(map);
    assertEquals(app.getStart(), appReConverted.getStart());
    assertEquals(app.getEnd(), appReConverted.getEnd());
    assertEquals(app.isAllDay(), appReConverted.isAllDay());
    assertEquals(allDay, appReConverted.isAllDay());
    assertEquals(app.getDescription(), appReConverted.getDescription());
    assertEquals(app.getTitle(), appReConverted.getTitle());
    assertEquals(app.getLocation(), appReConverted.getLocation());
    assertEquals(app.getCreatedBy(), appReConverted.getCreatedBy());
    assertEquals(app.getId(), appReConverted.getId());
  }

  @Test
  public void testAllDay() throws Exception {
    final Appointment app = createAppointment(true);
    convertAndTest(app, true);
  }

  @Test
  public void testFromToFrom() throws IOException, ValidationException {
    final boolean allDay = false;
    final Appointment initialApp = createAppointment(allDay);
    initialApp.setAllDay(allDay);
    checkConversionFromAndTo(initialApp, allDay);
  }

  @Ignore
  @Test
  public void testFromToFromAllDay() throws IOException, ValidationException {
    final boolean allDay = true;
    final Appointment initialApp = createAppointment(allDay);
    checkConversionFromAndTo(initialApp, allDay);
  }

  @Test
  public void testToMap() throws Exception {
    final Appointment app = createAppointment(false);
    convertAndTest(app, false);
  }

}
