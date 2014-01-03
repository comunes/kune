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
package cc.kune.common.client.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import cc.kune.common.client.notify.NotifyUser;

// TODO: Auto-generated Javadoc
/**
 * The Class Log.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class Log {

  /** The logger. */
  private static Logger logger = Logger.getLogger("KuneLog");

  /**
   * Debug.
   *
   * @param message the message
   */
  public static void debug(final String message) {
    log(Level.FINEST, message);
  }

  /**
   * Log.
   *
   * @param level the level
   * @param message the message
   */
  private static void log(Level level, final String message) {
    logger.log(level, message);
  }

  /**
   * Log.
   *
   * @param level the level
   * @param message the message
   * @param caught the caught
   */
  private static void log(Level level, final String message, final Throwable caught) {
    logger.log(level, message, caught);
  }

  /**
   * Debug.
   *
   * @param message the message
   * @param caught the caught
   */
  public static void debug(final String message, final Throwable caught) {
    log(Level.FINEST, message, caught);
  }

  /**
   * Error.
   *
   * @param message the message
   */
  public static void error(final String message) {
    log(Level.SEVERE, message);
    NotifyUser.logError(message);
  }

  /**
   * Error.
   *
   * @param message the message
   * @param caught the caught
   */
  public static void error(final String message, final Throwable caught) {
    log(Level.SEVERE, message, caught);
    NotifyUser.logError(message);
  }

  /**
   * Info.
   *
   * @param message the message
   */
  public static void info(final String message) {
    log(Level.INFO, message);
  }

  /**
   * Info.
   *
   * @param message the message
   * @param caught the caught
   */
  public static void info(final String message, final Throwable caught) {
    log(Level.INFO, message, caught);
  }

  /**
   * Warn.
   *
   * @param message the message
   * @param caught the caught
   */
  public static void warn(final String message, final Throwable caught) {
    log(Level.WARNING, message, caught);
    NotifyUser.logError(message);
  }

  /**
   * Warn.
   *
   * @param message the message
   */
  public static void warn(final String message) {
    log(Level.WARNING, message);
    NotifyUser.logError(message);
  }

}
