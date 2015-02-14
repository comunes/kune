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

package cc.kune.core.client.sitebar.spaces;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NarrowManagerTests {

  private static final boolean LOGGED = true;
  private static final boolean NOT_LOGGED = false;

  @Before
  public void before() {
  }

  @Test
  public void testBoot() {
    assertFalse(NarrowManager.shouldNarrowSwipeBeEnabled(LOGGED, null));
    assertFalse(NarrowManager.shouldNarrowBeVisible(LOGGED, null));
  }

  @Test
  public void testClosedVisibleWhenNotLogged() {
    assertFalse(NarrowManager.shouldNarrowBeVisible(NOT_LOGGED, Space.groupSpace));
    assertFalse(NarrowManager.shouldNarrowBeVisible(NOT_LOGGED, Space.publicSpace));
    assertFalse(NarrowManager.shouldNarrowBeVisible(NOT_LOGGED, Space.homeSpace));
  }

  @Test
  public void testNarrowVisibleWhenLogged() {
    assertTrue(NarrowManager.shouldNarrowBeVisible(LOGGED, Space.groupSpace));
    assertFalse(NarrowManager.shouldNarrowBeVisible(LOGGED, Space.homeSpace));
    assertTrue(NarrowManager.shouldNarrowBeVisible(LOGGED, Space.userSpace));
  }

  @Test
  public void testSwipeEnabledWhenLogged() {
    assertTrue(NarrowManager.shouldNarrowSwipeBeEnabled(LOGGED, Space.groupSpace));
    assertTrue(NarrowManager.shouldNarrowSwipeBeEnabled(LOGGED, Space.homeSpace));
    assertTrue(NarrowManager.shouldNarrowSwipeBeEnabled(LOGGED, Space.userSpace));
  }

  @Test
  public void testSwipeNotEnabledWhenNotLogged() {
    assertFalse(NarrowManager.shouldNarrowSwipeBeEnabled(NOT_LOGGED, Space.groupSpace));
    assertFalse(NarrowManager.shouldNarrowSwipeBeEnabled(NOT_LOGGED, Space.publicSpace));
    assertFalse(NarrowManager.shouldNarrowSwipeBeEnabled(NOT_LOGGED, Space.homeSpace));
  }
}
