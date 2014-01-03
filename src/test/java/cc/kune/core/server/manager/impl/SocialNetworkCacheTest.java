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

package cc.kune.core.server.manager.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cc.kune.domain.Group;
import cc.kune.domain.SocialNetworkData;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkCacheTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkCacheTest {

  /** The cache. */
  private SocialNetworkCache cache;

  /** The data. */
  private SocialNetworkData data;

  /** The group1. */
  private Group group1;

  /** The group2. */
  private Group group2;

  /** The user1. */
  private User user1;

  /** The user2. */
  private User user2;

  /**
   * Before.
   */
  @Before
  public void before() {
    cache = new SocialNetworkCache();
    user1 = new User();
    user2 = new User();
    user1.setShortName("user1");
    user2.setShortName("user2");
    group1 = new Group("group1", "group1");
    group2 = new Group("group2", "group2");
    data = new SocialNetworkData();
  }

  /**
   * Test basic add.
   */
  @Test
  public void testBasicAdd() {
    assertNull(cache.get(user1, group1));
    cache.put(user1, group1, data);
    cache.expire(group2);
    assertEquals(data, cache.get(user1, group1));
    assertEquals(1, cache.size());
  }

  /**
   * Test basic add dirty.
   */
  @Test
  public void testBasicAddDirty() {
    assertNull(cache.get(user1, group1));
    cache.put(user1, group1, data);
    cache.put(user2, group1, data);
    assertEquals(2, cache.size());
    cache.expire(group1);
    assertNull(cache.get(user1, group1));
    assertEquals(0, cache.size());
  }
}
