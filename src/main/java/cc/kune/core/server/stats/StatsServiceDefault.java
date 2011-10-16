package cc.kune.core.server.stats;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Group;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

public class StatsServiceDefault implements StatsService {

  private static final int LIMIT = 6;
  private final ContentFinder contentFinder;
  private final GroupFinder groupFinder;
  private final UserFinder userFinder;

  @Inject
  public StatsServiceDefault(final UserFinder userFinder, final GroupFinder groupFinder,
      final ContentFinder contentFinder) {
    this.userFinder = userFinder;
    this.groupFinder = groupFinder;
    this.contentFinder = contentFinder;
  }

  @Override
  public HomeStats getHomeStats() {
    return getHomeStats(Group.NO_GROUP);
  }

  @Override
  public HomeStats getHomeStats(final Group userGroup) {
    final HomeStats stats = new HomeStats();
    stats.setLastGroups(groupFinder.lastGroups(LIMIT, GroupType.CLOSED, GroupType.PERSONAL));
    stats.setTotalGroups(groupFinder.countGroups(GroupType.PERSONAL));
    stats.setTotalUsers(userFinder.count());
    stats.setLastPublishedContents(contentFinder.lastModifiedContents(LIMIT,
        ContentStatus.publishedOnline));
    if (userGroup != Group.NO_GROUP) {
      stats.setLastContentsOfMyGroups(contentFinder.lastModifiedContentsInUserGroup(LIMIT,
          userGroup.getId()));
    }
    return stats;
  }
}
