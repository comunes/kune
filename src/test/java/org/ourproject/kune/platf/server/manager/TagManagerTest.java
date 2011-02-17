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
package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.content.ContentManager;

import cc.kune.domain.Tag;
import cc.kune.domain.finders.TagFinder;

import com.google.inject.Inject;

public class TagManagerTest extends PersistenceTest {
    @Inject
    ContentManager contentManager;
    private Tag tag;
    @Inject
    TagFinder tagFinder;
    @Inject
    TagManager tagManager;

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

    @Before
    public void insertData() {
        openTransaction();
        tag = new Tag("foo1");
        tagManager.persist(tag);
    }

    @Test
    public void testTagCreation() {
        assertNotNull(tag.getId());
        assertNotNull(tagFinder.findByTagName("foo1"));
    }
}
