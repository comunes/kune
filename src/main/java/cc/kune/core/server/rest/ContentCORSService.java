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
import cc.kune.core.server.rack.filters.rest.REST;
import cc.kune.core.server.rpc.ContentRPC;
import cc.kune.core.shared.JSONConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentCORSService {
  private final ContentRPC contentRpc;
  private final UserSessionManager sessionManager;

  @Inject
  public ContentCORSService(final ContentRPC contentRpc, final UserSessionManager sessionManager) {
    this.contentRpc = contentRpc;
    this.sessionManager = sessionManager;
  }

  @REST(params = { JSONConstants.TOKEN_PARAM })
  public StateAbstractDTO getContent(final String token) {
    return contentRpc.getContent(sessionManager.getHash(), new StateToken(token));
  }

  @REST(params = { JSONConstants.TOKEN_PARAM })
  public StateAbstractDTO getContentByWaveRef(final String waveRef) {
    return contentRpc.getContentByWaveRef(sessionManager.getHash(), waveRef);
  }
}
