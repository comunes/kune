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

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

public class DateUtilsTest {

  private static final String SAMPLE = "2012-03-05T00:00:00.000+0100";
  private static final String SAMPLE2 = "2012-03-05T00:00:00.000+01:00";

  @Test
  public void basicTest() throws ParseException {

    DateUtils.toDate(SAMPLE);
    DateUtils.toDate(SAMPLE2);

    // Difference from GWT timezone and SimpleDate
    assertEquals("foo+0100", "foo+01:00".replaceFirst(DateUtils.TIMEZONE_REGEXP, "$1$2"));
  }
}
