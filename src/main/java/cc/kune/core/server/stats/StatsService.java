package cc.kune.core.server.stats;

import cc.kune.domain.Group;

public interface StatsService {

  HomeStats getHomeStats();

  HomeStats getHomeStats(Group userGroup);

}
