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
package cc.kune.common.shared.utils;

public class DateFormatConstants {

  public static final String DATE_EXPORT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";
  @Deprecated
  public static final String OLD1_DATE_EXPORT_FORMAT = "'DTSTART':yyyyMMdd'T'Hmmss";
  @Deprecated
  public static final String OLD2_DATE_EXPORT_FORMAT = "'DTSTART':yyyyMMdd'T'hhmmss";
  @Deprecated
  public static final String OLD3_DATE_EXPORT_FORMAT = "'DTSTART':yyyyMMdd'T'hhmm";

  public DateFormatConstants() {
    super();
  }

}
