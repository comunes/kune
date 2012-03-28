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
package cc.kune.events.shared;

import java.util.Map;

import cc.kune.common.client.utils.DateUtils;
import cc.kune.common.shared.res.ICalConstants;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * The Class EventsConversionUtil is used to convert Appointments to Gadgets
 * properties and viceversa
 */
public class EventsClientConversionUtil extends EventsSharedConversionUtil {

  public static Appointment toApp(final Map<String, String> properties) throws Exception {
    final Appointment app = EventsSharedConversionUtil.toApp(properties);
    final String start = properties.get(ICalConstants.DATE_TIME_START);
    if (start != null) {
      app.setStart(DateUtils.toDate(start));
    }
    final String end = properties.get(ICalConstants.DATE_TIME_END);
    if (end != null) {
      app.setEnd(DateUtils.toDate(end));
    }
    return app;
  }

  public static Map<String, String> toMap(final Appointment app) {
    final Map<String, String> properties = EventsSharedConversionUtil.toMap(app);
    properties.put(ICalConstants.DATE_TIME_START, DateUtils.toString(app.getStart()));
    properties.put(ICalConstants.DATE_TIME_END, DateUtils.toString(app.getEnd()));
    return properties;
  }

}
