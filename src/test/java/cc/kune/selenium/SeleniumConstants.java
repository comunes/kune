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
package cc.kune.selenium;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class SeleniumConstants.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class SeleniumConstants {

  /** The Constant FIREFOX_PROFILE_NAME. */
  public static final String FIREFOX_PROFILE_NAME = "selenium";

  /** The Constant GWTDEV. */
  public static final String GWTDEV = "gwt-debug-";

  /** The Constant INPUT. */
  public static final String INPUT = "-input";

  /** The Constant SCREENSHOTS_DIR. */
  public static final String SCREENSHOTS_DIR = "img" + File.separator + "screenshots" + File.separator;
  /* Configure this for use other lang, site, or driver */
  /** The Constant USER_EMAIL. */
  public static final String USER_EMAIL = "admin@example.org";

  /** The Constant USER_LONGNAME. */
  public static final String USER_LONGNAME = "Administrator";

  /** The Constant USER_PASSWD. */
  public static final String USER_PASSWD = "easyeasy";

  /** The Constant USER_SHORNAME. */
  public static final String USER_SHORNAME = "admin";

  /**
   * Instantiates a new selenium constants.
   */
  SeleniumConstants() {
    // Final class
  }
}
