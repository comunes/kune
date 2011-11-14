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
package cc.kune.core.server.rpc;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.stats.StatsService;
import cc.kune.core.shared.dto.HomeStatsDTO;
import cc.kune.hspace.client.ClientStatsService;

import com.google.inject.Inject;

public class StatsRPC implements RPC, ClientStatsService {

  private final Mapper mapper;
  private final StatsService statsService;
  private final UserSessionManager userSessionManager;

  @Inject
  public StatsRPC(final StatsService statsService, final UserSessionManager userSessionManager,
      final Mapper mapper) {
    this.statsService = statsService;
    this.userSessionManager = userSessionManager;
    this.mapper = mapper;
  }

  @Override
  public HomeStatsDTO getHomeStats() {
    return mapper.map(statsService.getHomeStats(), HomeStatsDTO.class);
  }

  @Override
  public HomeStatsDTO getHomeStats(final String userHash) {
    try {
      return getHomeStatsWrapper(userHash);
    } catch (final SessionExpiredException e) {
      // If the session is expired just send unauth stats
      return getHomeStats();
    }
  }

  @Authenticated
  public HomeStatsDTO getHomeStatsWrapper(final String userHash) {
    return mapper.map(statsService.getHomeStats(userSessionManager.getUser().getUserGroup()),
        HomeStatsDTO.class);
  }

}
