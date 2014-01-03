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
package cc.kune.events.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cc.kune.common.shared.utils.DateFormatConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DateUtils {

  /** The Constant FORMATTER. */
  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
      DateFormatConstants.DATE_EXPORT_FORMAT);

  /** The Constant TIMEZONE_REGEXP. */
  public static final String TIMEZONE_REGEXP = "(.*)\\:([0-9][0-9])$";

  /**
   * To date.
   * 
   * @param date
   *          the date
   * @return the date
   * @throws ParseException
   *           the parse exception
   */
  public static Date toDate(final String date) throws ParseException {
    try {
      return FORMATTER.parse(date);
    } catch (final ParseException e) {
      return FORMATTER.parse(date.replaceFirst(TIMEZONE_REGEXP, "$1$2"));
    }
  }

  /**
   * To string.
   * 
   * @param date
   *          the date
   * @return the string
   */
  public static String toString(final Date date) {
    return FORMATTER.format(date);
  }

}
