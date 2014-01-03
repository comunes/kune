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

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

import cc.kune.core.server.PersistenceTest;
import cc.kune.core.server.testhelper.ctx.DomainContext;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSocialNetworkManagerTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractSocialNetworkManagerTest extends PersistenceTest {

  /** The admin. */
  @Inject
  protected User admin;

  /** The ctx. */
  protected DomainContext ctx;

  /** The group. */
  protected Group group;

  /** The orphaned group. */
  protected Group orphanedGroup;

  /** The other user. */
  protected User otherUser;

  /** The user. */
  protected User user;

  /** The user group. */
  protected Group userGroup;

  /**
   * Assert social network is empty.
   */
  private void assertSocialNetworkIsEmpty() {
    assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 0);
    assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
    assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
  }

  /**
   * Inits the.
   */
  @Transactional
  @Before
  public void init() {
    openTransaction();
    ctx = new DomainContext();
    ctx.createUsers("user1");
    ctx.createUsers("admin");
    ctx.createUsers("otheruser");
    ctx.createGroups("group1");
    ctx.createOrphanGroup("grouporph");
    user = ctx.getUser("user1");
    group = ctx.getGroup("group1");
    orphanedGroup = ctx.getGroup("grouporph");
    userGroup = user.getUserGroup();
    admin = ctx.getUser("admin");
    otherUser = ctx.getUser("otheruser");
    assertSocialNetworkIsEmpty();
  }

  /**
   * Rollback.
   */
  @After
  public void rollback() {
    rollbackTransaction();
  }
}
