package cc.kune.core.server.rpc;

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
  @Authenticated
  public HomeStatsDTO getHomeStats(final String userHash) {
    return mapper.map(statsService.getHomeStats(userSessionManager.getUser().getUserGroup()),
        HomeStatsDTO.class);
  }

}
