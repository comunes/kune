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

import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.domain.User;

@Singleton
public class UsersHealthCheck extends HealthCheck {

  @Inject
  UserManager usersManager;
  @Inject
  KuneBasicProperties properties;

  @Override
  @KuneTransactional
  protected Result check() throws Exception {
    String adminName = properties.getAdminShortName();
    String password = properties.getAdminPassword();
    User admin;
    try {
      admin = usersManager.findByShortname(adminName);
    } catch (final NoResultException e) {
      return Result.unhealthy("Cannot retrieve admin user");
    }
    try {
      usersManager.findByShortname("somenastynamenotreal");
      return Result.unhealthy("User finders not working properly");
    } catch (final NoResultException e) {
      // continue
    }
    try {
      usersManager.find(admin.getId());
    } catch (final NoResultException e) {
      return Result.unhealthy("User find by id not working properly");
    }
    if (usersManager.search(admin.getShortName()).getSize() < 1) {
      return Result.unhealthy("User find by shortname not working properly");
    }
    if (usersManager.count() <= 0) {
      return Result.unhealthy("Cannot retrieve number of Users");
    }
    try {
      usersManager.getBuddiesPresence(admin);
    } catch (final Exception e) {
      return Result.unhealthy("Cannot retrieve buddies of admin user", e);
    }
    try {
      usersManager.login(adminName, password);
    } catch (final Exception e) {
      return Result.unhealthy("Cannot login as admin user", e);
    }

    return Result.healthy();
  }

}
