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
package cc.kune.core.server.integration;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.stats.HomeStats;
import cc.kune.core.server.stats.StatsService;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class StatsServiceTest extends IntegrationTest {
  @Inject
  GroupFinder groupFinder;
  @Inject
  StatsService statsService;

  private void checkStats(final HomeStats homeStats) {
    assertTrue(homeStats.getTotalGroups() > 0);
    assertTrue(homeStats.getTotalUsers() > 0);
  }

  @Transactional
  @Before
  public void init() {
    new IntegrationTestHelper(this);
  }

  @Test
  public void testBasicStats() {
    HomeStats homeStats = statsService.getHomeStats();
    checkStats(homeStats);
    homeStats = statsService.getHomeStats(groupFinder.findByShortName(getDefSiteShortName()));
    checkStats(homeStats);
  }

}
