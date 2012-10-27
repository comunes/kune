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
package cc.kune.events.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

public class DateUtils {

  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
      DateFormatConstants.DATE_EXPORT_FORMAT);
  public static final String TIMEZONE_REGEXP = "(.*)\\:([0-9][0-9])$";

  public static Date toDate(final String date) throws ParseException {
    try {
      return FORMATTER.parse(date);
    } catch (final ParseException e) {
      return FORMATTER.parse(date.replaceFirst(TIMEZONE_REGEXP, "$1$2"));
    }
  }

  public static String toString(final Date date) {
    return FORMATTER.format(date);
  }

}
