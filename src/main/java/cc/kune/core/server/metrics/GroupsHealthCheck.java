/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.server.metrics;

import javax.persistence.NoResultException;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.core.server.manager.GroupManager;
import cc.kune.domain.Group;

@Singleton
public class GroupsHealthCheck extends HealthCheck {

  @Inject
  GroupManager groupManager;

  @Override
  protected Result check() throws Exception {
    Group siteDefaultGroup;
    try {
      siteDefaultGroup = groupManager.getSiteDefaultGroup();
    } catch (final NoResultException e) {
      return Result.unhealthy("Cannot retrieve default group");
    }
    try {
      groupManager.findByShortName("somenastynamenotreal");
      return Result.unhealthy("Group finders not working properly");
    } catch (final NoResultException e) {
      // continue
    }
    try {
      groupManager.find(siteDefaultGroup.getId());
    } catch (final NoResultException e) {
      return Result.unhealthy("Group find by id not working properly");
    }
    if (groupManager.search(siteDefaultGroup.getShortName()).getSize() <= 0) {
      return Result.unhealthy("Group find by shortname not working properly");
    }
    if (groupManager.count() <= 0) {
      return Result.unhealthy("Cannot retrieve number of Groups");
    }
    return Result.healthy();
  }

}
