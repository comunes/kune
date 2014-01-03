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
package cc.kune.core.server.finders;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistenceTest;
import cc.kune.domain.Group;
import cc.kune.domain.finders.GroupFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupFinderTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupFinderTest extends PersistenceTest {

  /** The Constant LONG_NAME_A. */
  private static final String LONG_NAME_A = "long name a";

  /** The Constant LONG_NAME_B. */
  private static final String LONG_NAME_B = "long name b";

  /** The Constant LONG_NAME_C. */
  private static final String LONG_NAME_C = "long name c";

  /** The Constant SHORT_NAME_A. */
  private static final String SHORT_NAME_A = "shortnamea";

  /** The Constant SHORT_NAME_B. */
  private static final String SHORT_NAME_B = "shortnameb";

  /** The Constant SHORT_NAME_C. */
  private static final String SHORT_NAME_C = "shortnamec";

  /** The group finder. */
  @Inject
  GroupFinder groupFinder;

  /**
   * Close.
   */
  @After
  public void close() {
    closeTransaction();
  }

  /**
   * Insert data.
   */
  @Before
  public void insertData() {
    openTransaction();
    persist(new Group(SHORT_NAME_B, LONG_NAME_B));
    persist(new Group(SHORT_NAME_A, LONG_NAME_A));
    final Group groupC = new Group(SHORT_NAME_C, LONG_NAME_C);
    persist(groupC);
  }

  /**
   * Test count.
   */
  @Test
  public void testCount() {
    assertEquals(1, (long) groupFinder.countByLongName(LONG_NAME_B));
    assertEquals(1, (long) groupFinder.countByShortName(SHORT_NAME_A));
  }

  /**
   * Test get all.
   */
  @Test
  public void testGetAll() {
    assertEquals(3, groupFinder.getAll().size());

  }

  /**
   * Test sort.
   */
  @Test
  public void testSort() {
    assertEquals(SHORT_NAME_A, groupFinder.getAll().get(0).getShortName());
  }
}
