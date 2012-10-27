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
package cc.kune.selenium;

import java.io.File;

public final class SeleniumConstants {

  public static final String FIREFOX_PROFILE_NAME = "selenium";
  public static final String GWTDEV = "gwt-debug-";

  public static final String INPUT = "-input";
  public static final String SCREENSHOTS_DIR = "img" + File.separator + "screenshots" + File.separator;
  /* Configure this for use other lang, site, or driver */
  public static final String USER_EMAIL = "admin@example.org";
  public static final String USER_LONGNAME = "Administrator";
  public static final String USER_PASSWD = "easyeasy";
  public static final String USER_SHORNAME = "admin";

  SeleniumConstants() {
    // Final class
  }
}
