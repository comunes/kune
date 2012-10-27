/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.utils;

import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * The Class DateUtils try to follow ISO_8601 (previously RFC 2445 date-time
 * formats).
 */
public class DateUtils extends DateFormatConstants {

  private static final DateTimeFormat iso_8601 = DateTimeFormat.getFormat(DATE_EXPORT_FORMAT);

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

  public static String toString(final Date date) {
    return iso_8601.format(date);
  }
}
