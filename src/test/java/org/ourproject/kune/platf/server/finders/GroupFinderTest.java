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
package org.ourproject.kune.platf.server.finders;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;

import cc.kune.domain.Group;

import com.google.inject.Inject;

public class GroupFinderTest extends PersistenceTest {
    @Inject
    Group groupFinder;

    @Before
    public void insertData() {
        openTransaction();
        persist(new Group("shortname1", "name1"));
        persist(new Group("shortname2", "name2"));
    }

    @Test
    public void testGetAll() {
        assertEquals(2, groupFinder.getAll().size());
    }

    @After
    public void close() {
        closeTransaction();
    }
}
