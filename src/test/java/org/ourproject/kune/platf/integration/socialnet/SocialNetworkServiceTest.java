package org.ourproject.kune.platf.integration.socialnet;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;

import cc.kune.core.client.errors.AlreadyUserMemberException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateToken;

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
