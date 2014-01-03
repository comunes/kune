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
package cc.kune.core.server.stats;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Group;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class StatsServiceDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StatsServiceDefault implements StatsService {

  /** The Constant LIMIT. */
  private static final int LIMIT = 20; // Before 6

  /** The content finder. */
  private final ContentFinder contentFinder;

  /** The group finder. */
  private final GroupFinder groupFinder;

  /** The user finder. */
  private final UserFinder userFinder;

  /**
   * Instantiates a new stats service default.
   * 
   * @param userFinder
   *          the user finder
   * @param groupFinder
   *          the group finder
   * @param contentFinder
   *          the content finder
   */
  @Inject
  public StatsServiceDefault(final UserFinder userFinder, final GroupFinder groupFinder,
      final ContentFinder contentFinder) {
    this.userFinder = userFinder;
    this.groupFinder = groupFinder;
    this.contentFinder = contentFinder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.stats.StatsService#getHomeStats()
   */
  @Override
  public HomeStats getHomeStats() {
    return getHomeStats(Group.NO_GROUP);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.stats.StatsService#getHomeStats(cc.kune.domain.Group)
   */
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
