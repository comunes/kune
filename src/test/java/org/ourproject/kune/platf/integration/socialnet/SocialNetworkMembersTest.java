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
package org.ourproject.kune.platf.integration.socialnet;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class SocialNetworkMembersTest extends IntegrationTest {
    @Inject
    UserSession session;
    @Inject
    SocialNetworkManager socialNetManager;
    @Inject
    GroupManager groupFinder;

    private Group group;

    @Transactional
    @Before
    public void init() {
        new IntegrationTestHelper(this);
        group = groupFinder.findByShortName(getSiteAdminShortName());
    }

    @Test
    public void testAdminMembersOfGroupFinder() throws Exception {
        doLogin();
        final List<Group> result = groupFinder.findAdminInGroups(group.getId());
        assertEquals(2, result.size());
    }

    @Test
    public void testCollabMembersOfGroupFinder() throws Exception {
        doLogin();
        final List<Group> result = groupFinder.findCollabInGroups(group.getId());
        assertEquals(0, result.size());
    }

}
