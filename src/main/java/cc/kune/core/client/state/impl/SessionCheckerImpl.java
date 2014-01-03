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

import cc.kune.common.client.log.Log;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.SessionInstance;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SessionCheckerImpl implements SessionChecker {

  private final Provider<UserServiceAsync> service;

  @Inject
  public SessionCheckerImpl(final Provider<UserServiceAsync> service) {
    this.service = service;
  }

  @Override
  public void check(final AsyncCallback<Void> callback) {
    final String userHash = SessionInstance.get().getUserHash();
    Log.debug("Checking session (userhash: " + userHash + ")");
    service.get().onlyCheckSession(userHash, callback);
  }

}
