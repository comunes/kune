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
package cc.kune.core.shared;

public final class SessionConstants {

  public final static int _AN_HOUR = 1000 * 60 * 60;
  public final static int _5_HOURS = 5 * _AN_HOUR;
  public final static long A_DAY = _AN_HOUR * 24;
  public final static long ANON_SESSION_DURATION = A_DAY;
  public final static long ANON_SESSION_DURATION_AFTER_REG = A_DAY * 365;
  public static final String JSESSIONID = "JSESSIONID";
  // session duration
  public final static long SESSION_DURATION = A_DAY * 30; // four weeks login
  // public final static long SESSION_DURATION = 100; // For test
  public final static String USERHASH = "k007userHash";

  public SessionConstants() {

  }
}