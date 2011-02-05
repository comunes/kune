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
package org.ourproject.kune.platf.server.manager.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.testhelper.ctx.DomainContext;

import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;

public abstract class AbstractSocialNetworkManagerTest extends PersistenceTest {
    @Inject
    protected User admin;
    protected DomainContext ctx;
    protected Group group;
    protected Group orphanedGroup;
    protected User otherUser;
    protected User user;
    protected Group userGroup;

    private void assertSocialNetworkIsEmpty() {
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

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
}