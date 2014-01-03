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
package cc.kune.core.server.rpc;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.mapper.KuneMapper;
import cc.kune.core.server.stats.StatsService;
import cc.kune.core.shared.dto.HomeStatsDTO;
import cc.kune.hspace.client.ClientStatsService;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsRPC.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StatsRPC implements RPC, ClientStatsService {

  /** The mapper. */
  private final KuneMapper mapper;

  /** The stats service. */
  private final StatsService statsService;

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new stats rpc.
   * 
   * @param statsService
   *          the stats service
   * @param userSessionManager
   *          the user session manager
   * @param mapper
   *          the mapper
   */
  @Inject
  public StatsRPC(final StatsService statsService, final UserSessionManager userSessionManager,
      final KuneMapper mapper) {
    this.statsService = statsService;
    this.userSessionManager = userSessionManager;
    this.mapper = mapper;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.hspace.client.ClientStatsService#getHomeStats()
   */
  @Override
  public HomeStatsDTO getHomeStats() {
    return mapper.map(statsService.getHomeStats(), HomeStatsDTO.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.ClientStatsService#getHomeStats(java.lang.String)
   */
  @Override
  public HomeStatsDTO getHomeStats(final String userHash) {
    try {
      return getHomeStatsWrapper(userHash);
    } catch (final SessionExpiredException e) {
      // If the session is expired just send unauth stats
      return getHomeStats();
    }
  }

  /**
   * Gets the home stats wrapper.
   * 
   * @param userHash
   *          the user hash
   * @return the home stats wrapper
   */
  @Authenticated
  public HomeStatsDTO getHomeStatsWrapper(final String userHash) {
    return mapper.map(statsService.getHomeStats(userSessionManager.getUser().getUserGroup()),
        HomeStatsDTO.class);
  }

}
