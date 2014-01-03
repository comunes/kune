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
package cc.kune.core.client.state;

import cc.kune.core.shared.domain.utils.StateToken;

/**
 * The Interface HistoryWrapper controls the url #hash of the browser.
 * 
 * More info: http://en.wikipedia.org/wiki/Fragment_identifier
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface HistoryWrapper {

  /**
   * Check if the current #fragment token has #!hashbang and if not add it.
   */
  void checkHashbang();

  /**
   * Gets the current #token fragment of the current browser url.
   * 
   * @return the token
   */
  String getToken();

  /**
   * Goes to this new #group.tool.id.id token
   * 
   * @param historyToken
   *          the new history token fragment
   */
  void newItem(StateToken token);

  /**
   * Goes to this new #token fragment in the browser.
   * 
   * @param historyToken
   *          the history token
   */
  void newItem(final String historyToken);

  /**
   * Goes to this new #fragment and fire and event.
   * 
   * @param historyToken
   *          the new history token fragment
   * @param issueEvent
   *          should it fire an event?
   */
  void newItem(String historyToken, boolean issueEvent);

}