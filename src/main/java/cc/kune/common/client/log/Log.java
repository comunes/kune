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
package cc.kune.common.client.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import cc.kune.common.client.notify.NotifyUser;

public class Log {

  private static Logger logger = Logger.getLogger("KuneLog");

  public static void debug(final String message) {
    log(Level.FINEST, message);
  }

  private static void log(Level level, final String message) {
    logger.log(level, message);
  }

  private static void log(Level level, final String message, final Throwable caught) {
    logger.log(level, message, caught);
  }

  public static void debug(final String message, final Throwable caught) {
    log(Level.FINEST, message, caught);
  }

  public static void error(final String message) {
    log(Level.SEVERE, message);
    NotifyUser.logError(message);
  }

  public static void error(final String message, final Throwable caught) {
    log(Level.SEVERE, message, caught);
    NotifyUser.logError(message);
  }

  public static void info(final String message) {
    log(Level.INFO, message);
  }

  public static void info(final String message, final Throwable caught) {
    log(Level.INFO, message, caught);
  }

  public static void warn(final String message, final Throwable caught) {
    log(Level.WARNING, message, caught);
    NotifyUser.logError(message);
  }

  public static void warn(final String message) {
    log(Level.WARNING, message);
    NotifyUser.logError(message);
  }

}
