package org.ourproject.kune.platf.integration.socialnet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
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
	groupShortName = getDefGroupName();
    }

    @Test
    public void testRequestJoinPersonalGroup() throws SerializableException {
	doLogin();
	final String result = socialNetworkService.requestJoinGroup(session.getHash(), groupShortName);
	assertEquals(SocialNetworkDTO.REQ_JOIN_DENIED, result);
    }
}
