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
package cc.kune.core.shared;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionConstants.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class SessionConstants {

  /** The Constant _AN_HOUR in millisecons */
  public final static int _1_HOUR = 1000 * 60 * 60;

  /** The Constant _2_HOURS in millisecons */
  public final static int _2_HOURS = 2 * _1_HOUR;

  /** The Constant _5_HOURS in millisecons */
  public final static int _5_HOURS = 5 * _1_HOUR;

  /** The Constant A_DAY in millisecons */
  public final static long A_DAY = _1_HOUR * 24;

  /** The Constant A_WEEK in millisecons */
  public final static long A_WEEK = A_DAY * 7;

  /** The Constant ANON_SESSION_DURATION. */
  public final static long ANON_SESSION_DURATION = A_DAY;

  /** The Constant ANON_SESSION_DURATION_AFTER_REG. */
  public final static long ANON_SESSION_DURATION_AFTER_REG = A_DAY * 365;

  /**
   * Use http://kune.example.com/?dev=true to get some development functionality
   */
  public final static String DEVELOPMENT = "dev";

  /** The Constant JSESSIONID. */
  public static final String JSESSIONID = "JSESSIONID";

  /**
   * The Constant MIN_SIGN_IN_FOR_NEWBIES (number of access to kune under that,
   * the user is viewed as a newbie (so, more help is needed)
   */
  public final static long MIN_SIGN_IN_FOR_NEWBIES = 10;
  // session duration
  /** The Constant SESSION_DURATION. */
  public final static long SESSION_DURATION = A_DAY * 30; // four weeks login
  // public final static long SESSION_DURATION = 10000; // For test
  /** The Constant USERHASH. */
  public final static String USERHASH = "k007userHash";

  /**
   * Instantiates a new session constants.
   */
  public SessionConstants() {

  }
}
