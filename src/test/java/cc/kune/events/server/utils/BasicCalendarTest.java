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

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.Dates;

public class BasicCalendarTest {

  protected static final String DESCRIPTION = "some description";
  protected static final String ID = "some id";
  protected static final String TITLE = "some title";

  // protected Appointment createAppointment(final boolean allDay) {
  // final Appointment initialApp = new Appointment();
  // initialApp.setTitle(TITLE);
  // initialApp.setDescription(DESCRIPTION);
  // initialApp.setStart(getNow());
  // initialApp.setEnd(getNow());
  // initialApp.setId(ID);
  // initialApp.setAllDay(allDay);
  // return initialApp;
  // }

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
