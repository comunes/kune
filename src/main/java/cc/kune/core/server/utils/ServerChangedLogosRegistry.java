/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.server.utils;

import java.util.Date;

import cc.kune.core.server.persist.CachedCollection;
import cc.kune.core.shared.utils.ChangedLogosRegistry;

import com.google.inject.Singleton;

@Singleton
public class ServerChangedLogosRegistry implements ChangedLogosRegistry {

  private final CachedCollection<String, String> recentlyChanged;

  public ServerChangedLogosRegistry() {
    recentlyChanged = new CachedCollection<String, String>(100);
  }

  @Override
  public void add(final String token) {
    recentlyChanged.put(token, ((Long) new Date().getTime()).toString());
  }

  @Override
  public String getLastModified(final String token) {
    return recentlyChanged.get(token);
  }

  @Override
  public boolean isRecentlyChanged(final String token) {
    return recentlyChanged.containsKey(token);
  }

}
