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

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

import cc.kune.core.client.errors.AlreadyUserMemberException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.server.UserSession;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;

import com.google.inject.Inject;

public class SocialNetworkServiceTest extends IntegrationTest {
    @Inject
    UserSession session;
    @Inject
    SocialNetworkService socialNetworkService;

    private StateToken groupToken;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
        groupToken = new StateToken(getDefSiteGroupName());
    }

    @Test
    public void testGetGroupMembersNotLogged() throws Exception {
        final SocialNetworkDataDTO sn = socialNetworkService.getSocialNetwork(null, groupToken);
        assertNotNull(sn.getGroupMembers());
    }

    @Test
    public void testGetParticipationNotLogged() throws Exception {
        final SocialNetworkDataDTO sn = socialNetworkService.getSocialNetwork(null, groupToken);
        assertNotNull(sn.getUserParticipation());
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testRequestJoinNoLogged() throws Exception {
        socialNetworkService.requestJoinGroup(session.getHash(), groupToken);
    }

    @Test(expected = AlreadyUserMemberException.class)
    public void testRequestJoinPersonalGroup() throws Exception {
        doLogin();
        socialNetworkService.requestJoinGroup(session.getHash(), groupToken);
    }

}
