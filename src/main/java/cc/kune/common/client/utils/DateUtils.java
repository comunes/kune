/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.common.client.utils;

import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

import com.google.gwt.i18n.client.DateTimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtils try to follow ISO_8601 (previously RFC 2445 date-time
 * formats).
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DateUtils extends DateFormatConstants {

  /** The Constant iso_8601. */
  private static final DateTimeFormat iso_8601 = DateTimeFormat.getFormat(DATE_EXPORT_FORMAT);

  /**
   * To date.
   *
   * @param date the date
   * @return the date
   */
  public static Date toDate(final String date) {
    try {
      return iso_8601.parse(date);
    } catch (final IllegalArgumentException e) {
      try {
        // try old (buggy) hour formats
        // Sample DTSTART:19980118T230000
        return DateTimeFormat.getFormat(OLD1_DATE_EXPORT_FORMAT).parse(date);
      } catch (final IllegalArgumentException e1) {
        // DTSTART:20120229T120000
        try {
          return DateTimeFormat.getFormat(OLD2_DATE_EXPORT_FORMAT).parse(date);
        } catch (final IllegalArgumentException e2) {
          // DTSTART:20120225T1300
          return DateTimeFormat.getFormat(OLD3_DATE_EXPORT_FORMAT).parse(date);
        }
      }
    }
  }

  /**
   * To string.
   *
   * @param date the date
   * @return the string
   */
  public static String toString(final Date date) {
    return iso_8601.format(date);
  }
}
