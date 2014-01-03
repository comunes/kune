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
package cc.kune.core.client.sn.actions.conditions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class IsNotMeConditionTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RunWith(JukitoRunner.class)
public class IsNotMeConditionTest {

  /** The descr. */
  @Inject
  GuiActionDescrip descr;

  /** The group. */
  GroupDTO group;

  /** The is me condition. */
  private IsMeCondition isMeCondition;

  /** The is not me condition. */
  private IsNotMeCondition isNotMeCondition;

  /** The me. */
  UserSimpleDTO me;

  /** The my group. */
  GroupDTO myGroup;

  /** The other group. */
  GroupDTO otherGroup;

  /** The other user. */
  UserSimpleDTO otherUser;

  /** The session. */
  @Inject
  Session session;

  /**
   * Before.
   */
  @Before
  public void before() {
    this.me = Mockito.mock(UserSimpleDTO.class);
    this.otherUser = Mockito.mock(UserSimpleDTO.class);
    this.myGroup = Mockito.mock(GroupDTO.class);
    this.otherGroup = Mockito.mock(GroupDTO.class);
    // GWTMockUtilities.
    isNotMeCondition = new IsNotMeCondition(session);
    isMeCondition = new IsMeCondition(session);
    when(me.getShortName()).thenReturn("me");
    when(otherUser.getShortName()).thenReturn("other");
    when(myGroup.getShortName()).thenReturn("me");
    when(otherGroup.getShortName()).thenReturn("otherGroup");
    when(session.getCurrentUser()).thenReturn(me);
  }

  /**
   * Login.
   */
  private void login() {
    when(session.getCurrentUser()).thenReturn(me);
    when(session.isLogged()).thenReturn(true);
    when(session.isNotLogged()).thenReturn(false);
  }

  /**
   * Logout.
   */
  private void logout() {
    when(session.getCurrentUser()).thenReturn(null);
    when(session.isLogged()).thenReturn(false);
    when(session.isNotLogged()).thenReturn(true);
  }

  /**
   * Should be added.
   * 
   * @param mustBeAdded
   *          the must be added
   */
  private void shouldBeAdded(final boolean mustBeAdded) {
    assertTrue(mustBeAdded);
  }

  /**
   * Should not be added.
   * 
   * @param mustNotBeAdded
   *          the must not be added
   */
  private void shouldNotBeAdded(final boolean mustNotBeAdded) {
    assertFalse(mustNotBeAdded);
  }

  /**
   * Test is me my group.
   */
  @Test
  public void testIsMeMyGroup() {
    login();
    when(descr.getTarget()).thenReturn(myGroup);
    shouldBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me my group not logged.
   */
  @Test
  public void testIsMeMyGroupNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(myGroup);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me other group.
   */
  @Test
  public void testIsMeOtherGroup() {
    login();
    when(descr.getTarget()).thenReturn(otherGroup);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me other group not logged.
   */
  @Test
  public void testIsMeOtherGroupNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(otherGroup);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me other user.
   */
  @Test
  public void testIsMeOtherUser() {
    login();
    when(descr.getTarget()).thenReturn(otherUser);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me other user not logged.
   */
  @Test
  public void testIsMeOtherUserNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(otherUser);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me user.
   */
  @Test
  public void testIsMeUser() {
    login();
    when(descr.getTarget()).thenReturn(me);
    shouldBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is me user not logged.
   */
  @Test
  public void testIsMeUserNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(me);
    shouldNotBeAdded(isMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me me user.
   */
  @Test
  public void testIsNotMeMeUser() {
    login();
    when(descr.getTarget()).thenReturn(me);
    shouldNotBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me my group.
   */
  @Test
  public void testIsNotMeMyGroup() {
    login();
    when(descr.getTarget()).thenReturn(myGroup);
    shouldNotBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me my group not logged.
   */
  @Test
  public void testIsNotMeMyGroupNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(myGroup);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me other group.
   */
  @Test
  public void testIsNotMeOtherGroup() {
    login();
    when(descr.getTarget()).thenReturn(otherGroup);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me other group not logged.
   */
  @Test
  public void testIsNotMeOtherGroupNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(otherGroup);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me other user.
   */
  @Test
  public void testIsNotMeOtherUser() {
    login();
    when(descr.getTarget()).thenReturn(otherUser);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test is not me other user not logged.
   */
  @Test
  public void testIsNotMeOtherUserNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(otherUser);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }

  /**
   * Test me user not logged.
   */
  @Test
  public void testMeUserNotLogged() {
    logout();
    when(descr.getTarget()).thenReturn(me);
    shouldBeAdded(isNotMeCondition.mustBeAdded(descr));
  }
}
