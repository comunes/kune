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

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtilsTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DateUtilsTest {

  /** The Constant SAMPLE. */
  private static final String SAMPLE = "2012-03-05T00:00:00.000+0100";

  /** The Constant SAMPLE2. */
  private static final String SAMPLE2 = "2012-03-05T00:00:00.000+01:00";

  /**
   * Basic test.
   * 
   * @throws ParseException
   *           the parse exception
   */
  @Test
  public void basicTest() throws ParseException {

    DateUtils.toDate(SAMPLE);
    DateUtils.toDate(SAMPLE2);

    // Difference from GWT timezone and SimpleDate
    assertEquals("foo+0100", "foo+01:00".replaceFirst(DateUtils.TIMEZONE_REGEXP, "$1$2"));
  }
}
