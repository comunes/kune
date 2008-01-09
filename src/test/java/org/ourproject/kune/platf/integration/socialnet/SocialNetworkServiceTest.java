package org.ourproject.kune.platf.integration.socialnet;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.errors.AlreadyUserMemberException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SocialNetworkServiceTest extends IntegrationTest {
    @Inject
    UserSession session;
    @Inject
    SocialNetworkService socialNetworkService;

    private String groupShortName;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
        groupShortName = getDefSiteGroupName();
    }

    @Test(expected = AlreadyUserMemberException.class)
    public void testRequestJoinPersonalGroup() throws SerializableException {
        doLogin();
        socialNetworkService.requestJoinGroup(session.getHash(), groupShortName);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testRequestJoinNoLogged() throws SerializableException {
        socialNetworkService.requestJoinGroup(session.getHash(), groupShortName);
    }

    @Test
    public void testGetGroupMembersNotLogged() throws SerializableException {
        SocialNetworkDTO groupMembers = socialNetworkService.getGroupMembers(null, groupShortName);
        assertNotNull(groupMembers);
    }

    @Test
    public void testGetParticipationNotLogged() throws SerializableException {
        ParticipationDataDTO participation = socialNetworkService.getParticipation(null, groupShortName);
        assertNotNull(participation);
    }

}
