/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.stats;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Group;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

public class StatsServiceDefault implements StatsService {

  private static final int LIMIT = 20; // Before 6
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
        ContentStatus.publishedOnline, GroupType.CLOSED));
    if (userGroup != Group.NO_GROUP) {
      stats.setLastContentsOfMyGroups(contentFinder.lastModifiedContentsInUserGroup(LIMIT,
          userGroup.getId()));
    }
    return stats;
  }
}
