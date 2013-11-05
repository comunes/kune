/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.server.manager.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.Pair;
import cc.kune.core.server.LogThis;
import cc.kune.core.server.persist.CachedCollection;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.SocialNetworkData;
import cc.kune.domain.User;

import com.google.inject.Singleton;

/**
 * The Class SocialNetworkCache create a cache for maker faster the retrieve of
 * the SN of a user
 */
@LogThis
@Singleton
public class SocialNetworkCache extends CachedCollection<Pair<User, Group>, SocialNetworkData> {
  public static final Log LOG = LogFactory.getLog(SocialNetworkCache.class);

  /** The expired groups. */
  private final Set<Group> expiredGroups;

  /**
   * Instantiates a new social network cache.
   */
  public SocialNetworkCache() {
    super(100);
    expiredGroups = Collections.synchronizedSet(new HashSet<Group>());
  }

  /**
   * Adds a group which SN is modified, so we should not use the cache for that.
   * 
   * @param group
   *          the group
   */
  public void expire(final Group group) {
    SocialNetwork sn = group.getSocialNetwork();
    AccessLists acl = sn.getAccessLists();
    for (Group admins : acl.getAdmins().getList()) {
      expiredGroups.add(admins);
    }
    for (Group editors : acl.getEditors().getList()) {
      expiredGroups.add(editors);
    }
    for (Group viewers : acl.getViewers().getList()) {
      expiredGroups.add(viewers);
    }
    for (Group pending : sn.getPendingCollaborators().getList()) {
      expiredGroups.add(pending);
    }
    expiredGroups.add(group);
  }

  /**
   * Gets the SN of some user/group from the cache if available
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @return the social network data
   */
  public SocialNetworkData get(final User user, final Group group) {
    final Pair<User, Group> pair = Pair.create(user, group);
    if (expiredGroups.contains(group)) {
      // TODO Do this in a cron job
      final Iterator<Pair<User, Group>> iterator = super.keySet().iterator();
      while (iterator.hasNext()) {
        if (iterator.next().right.equals(group)) {
          iterator.remove();
        }
      }
      expiredGroups.remove(group);
      LOG.debug("Not returning expired SN info");
      return null;
    }
    return super.get(pair);
  }

  public Object put(final User user, final Group group, final SocialNetworkData data) {
    return super.put(Pair.create(user, group), data);
  }
}
