/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.common.shared.res;

public class ICalConstants {

  /* Note that all day events is not supported by ICalendar */
  public static final String _ALL_DAY = "ALLDAY";
  public static final String _INTERNAL_ID = "INTERNALID";
  // VTIMEZONE ?? See: TimeZoneConstants in GWT for names and values
  public static final String DATE_TIME_END = "DTEND";
  public static final String DATE_TIME_START = "DTSTART";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String LOCATION = "LOCATION";
  public static final String ORGANIZER = "ORGANIZER";
  public static final String SUMMARY = "SUMMARY";
  public static final String UID = "UID";
  public static final String[] ZTOTAL_LIST = new String[] { DATE_TIME_START, DATE_TIME_END, DESCRIPTION,
      LOCATION, ORGANIZER, SUMMARY, _ALL_DAY, _INTERNAL_ID, UID };
}
