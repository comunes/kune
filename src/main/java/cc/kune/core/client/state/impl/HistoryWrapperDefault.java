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

import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.History;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryWrapperDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HistoryWrapperDefault implements HistoryWrapper {

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.HistoryWrapper#checkHashbang()
   */
  @Override
  public void checkHashbang() {
    final String currentToken = History.getToken();
    if (!currentToken.startsWith("!")) {
      History.newItem(HistoryUtils.hashbang(currentToken), false);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.HistoryWrapper#getToken()
   */
  @Override
  public String getToken() {
    return HistoryUtils.undoHashbang(History.getToken());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.HistoryWrapper#newItem(cc.kune.core.shared.domain
   * .utils.StateToken)
   */
  @Override
  public void newItem(final StateToken token) {
    newItem(token.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.HistoryWrapper#newItem(java.lang.String)
   */
  @Override
  public void newItem(final String historyToken) {
    History.newItem(HistoryUtils.hashbang(historyToken));
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.HistoryWrapper#newItem(java.lang.String,
   * boolean)
   */
  @Override
  public void newItem(final String historyToken, final boolean issueEvent) {
    History.newItem(HistoryUtils.hashbang(historyToken), issueEvent);
  }

}
