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
package cc.kune.core.server.rest;

import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.manager.SiteManager;
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SiteCORSService {
  private final SiteManager manager;
  private final UserSessionManager sessionManager;

  @Inject
  public SiteCORSService(final SiteManager manager, final UserSessionManager sessionManager) {
    this.manager = manager;
    this.sessionManager = sessionManager;
  }

  @REST(params = {})
  public InitDataDTO getInitData() {
    final String hash = sessionManager.getHash();
    final InitDataDTO initData = manager.getInitData(hash);
    final UserInfoDTO userInfo = initData.getUserInfo();
    if (userInfo != null) {
      userInfo.setUserHash(hash);
    }
    return initData;
  }

  @REST(params = {})
  public void logout() {
    sessionManager.logout();
  }
}
