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
package cc.kune.core.client.state.impl;

/**
 * The Class HistoryUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HistoryUtils {

  /** The Constant PREFIX. */
  public static final String PREFIX = "!";

  /**
   * Hashbang makes #!hash googleables
   * https://developers.google.com/webmasters/ajax-crawling/docs/getting-started
   * 
   * @param historyToken
   *          the #history token fragment
   * @return the #!history token fragment with ! added
   */
  public static String hashbang(final String historyToken) {
    return historyToken == null || historyToken.startsWith(PREFIX) ? historyToken : PREFIX
        + historyToken;
  }

  /**
   * Undo hashbang (removes ! from a #!hash).
   * 
   * @param historyToken
   *          the history token
   * @return the #!fragment history token without !
   */
  public static String undoHashbang(final String historyToken) {
    return historyToken != null && historyToken.startsWith(PREFIX) ? historyToken.substring(1)
        : historyToken;
  }

}
